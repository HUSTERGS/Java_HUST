package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.*;

/**
 * AbstractIndex的具体实现类
 */
public class Index extends AbstractIndex {
    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("docId-----docPath mapping\n");
        for (Map.Entry<Integer, String> entry : docIdToDocPathMapping.entrySet()) {
            builder.append(entry.getKey());
            builder.append("\t---->\t");
            builder.append(entry.getValue());
            builder.append("\n");
        }
        builder.append("PostingList: \n");
        for (Map.Entry<AbstractTerm, AbstractPostingList> entry : termToPostingListMapping.entrySet()) {
            builder.append(entry.getKey().toString());
            builder.append("\t---->\t");
            builder.append(entry.getValue().toString());
        }
        return builder.toString();
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
        for (AbstractTermTuple termTuple : document.getTuples()) {
            if (!termToPostingListMapping.containsKey(termTuple.term)) {
                // 如果不含有键的话
                Posting posting = new Posting();
                posting.setDocId(document.getDocId());
                posting.setFreq(termTuple.freq);
                List<Integer> positions = new ArrayList<>();
                positions.add(termTuple.curPos);
                posting.setPositions(positions);
                termToPostingListMapping.put(termTuple.term, new PostingList());
                termToPostingListMapping.get(termTuple.term).add(posting);
            } else {
                // 如果含有键的话,先获得已经存储的index,再将目前的curPos加入
                int index = termToPostingListMapping.get(termTuple.term).indexOf(document.getDocId());
                if (index == -1) {
                    // 如果有键，但是没有相应的posting
                    Posting posting = new Posting();
                    posting.setDocId(document.getDocId());
                    posting.setFreq(termTuple.freq);
                    List<Integer> positions = new ArrayList<>();
                    positions.add(termTuple.curPos);
                    posting.setPositions(positions);
                    termToPostingListMapping.get(termTuple.term).add(posting);
                } else {
                    termToPostingListMapping.get(termTuple.term).get(index).getPositions().add(termTuple.curPos);
                    termToPostingListMapping.get(termTuple.term).get(index).setFreq(
                            termToPostingListMapping.get(termTuple.term).get(index).getFreq() + 1
                    );
                }
            }
        }

        // 顺便再调用一下optimize()
        optimize();
    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        try {
            readObject(new ObjectInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        try {
            writeObject(new ObjectOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        // FIXME: 不清楚如果不存在会不会报错  search method ,从返回map中指定键对应的值
        return termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        // FIXME: 不确定是不是对的, 返回keySet
        return termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
        for (Map.Entry<AbstractTerm, AbstractPostingList> entry: termToPostingListMapping.entrySet()) {
            // 先对term根据position排序
            for (int i = 0; i < entry.getValue().size(); i++) {
                entry.getValue().get(i).sort();
            }
            // 再整体对docID排序
            entry.getValue().sort();
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return docIdToDocPathMapping.get(docId);
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {

        try {
            out.writeObject(docIdToDocPathMapping.size());
            out.writeObject(termToPostingListMapping.size());
            for (Map.Entry<Integer, String> entry : docIdToDocPathMapping.entrySet()) {
                out.writeObject(entry.getKey());
                out.writeObject(entry.getValue());
            }
            for (Map.Entry<AbstractTerm, AbstractPostingList> entry : termToPostingListMapping.entrySet()) {
                entry.getKey().writeObject(out);
                entry.getValue().writeObject(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        int dtpSize = 0;
        int ttpSize = 0;
        try {
            dtpSize = (Integer) in.readObject();
            ttpSize = (Integer) in.readObject();
            for(int i = 0; i < dtpSize; i++) {
                Integer docId = (Integer) in.readObject();
                String docPath = (String) in.readObject();
                docIdToDocPathMapping.put(docId, docPath);
            }
            for (int i = 0; i < ttpSize; i++) {
                AbstractTerm term = new Term();
                term.readObject(in);
                AbstractPostingList postingList = new PostingList();
                postingList.readObject(in);
                termToPostingListMapping.put(term, postingList);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    /*
    * 将index以文本形式写入，默认位置为index.txt
    * */
    public void writePlainText(File file){
        try {
            new BufferedWriter(new FileWriter(file)).write(toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
