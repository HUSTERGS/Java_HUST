package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Posting extends AbstractPosting {
    /**
     * 缺省构造函数
     */
    public Posting() {}

    /**
     * 构造函数
     * @param docId ：包含单词的文档id
     * @param freq  ：单词在文档里出现的次数
     * @param positions   ：单词在文档里出现的位置
     */
    public Posting(int docId, int freq, List<Integer> positions){
        super(docId, freq, positions);
    }

    /**
     * 判断二个Posting内容是否相同
     * @param obj ：要比较的另外一个Posting
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Posting) {
            // 比较先比较size，减少后续的containAll函数调用
            return this.docId == ((Posting) obj).docId &&
                    this.freq == ((Posting) obj).freq &&
                    this.positions.size() == ((Posting) obj).positions.size() &&
                    this.positions.containsAll(((Posting) obj).positions) &&
                    ((Posting) obj).positions.containsAll(this.positions);
        }
        return false;
    }

    /**
     * 返回Posting的字符串表示
     * @return 字符串
     */
    @Override
    public String toString() {
        return "{ docId: " + docId + ", freq: " + freq + ", positions: " + positions + " }";
    }

    /**
     * 返回包含单词的文档id
     * @return ：文档id
     */
    @Override
    public int getDocId() {
        return this.docId;
    }

    /**
     * 设置包含单词的文档id
     * @param docId：包含单词的文档id
     */
    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    /**
     * 返回单词在文档里出现的次数
     * @return ：出现次数
     */
    @Override
    public int getFreq() {
        return this.freq;
    }

    /**
     * 设置单词在文档里出现的次数
     * @param freq:单词在文档里出现的次数
     */
    @Override
    public void setFreq(int freq) {
        this.freq = freq;
    }

    /**
     * 返回单词在文档里出现的位置列表
     * @return ：位置列表
     */
    @Override
    public List<Integer> getPositions() {
        return positions;
    }

    /**
     * 设置单词在文档里出现的位置列表
     * @param positions：单词在文档里出现的位置列表
     */
    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    /**
     * 比较二个Posting对象的大小（根据docId）
     * @param o： 另一个Posting对象
     * @return ：二个Posting对象的docId的差值
     */
    @Override
    public int compareTo(AbstractPosting o) {
        // FIXME: not sure about compareTo method of Posting, which return the difference of docID
        return this.docId - o.getDocId();
    }

    /**
     * 对内部positions从小到大排序
     */
    @Override
    public void sort() {
        Collections.sort(this.positions);
    }

    /**
     * 写到二进制文件
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(positions.size());
            for (Integer i : positions) {
                out.writeObject(i);
            }
            out.writeObject(docId);
            out.writeObject(freq);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            int size = (Integer) in.readObject();
            for (int i = 0 ; i < size; i++) {
                positions.add((Integer) in.readObject());
            }
            this.docId = (Integer) in.readObject();
            this.freq = (Integer) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
