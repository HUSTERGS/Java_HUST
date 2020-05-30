package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import java.util.List;

/**
 *<pre>
 *     Document是文档对象的具体子类.
 *          文档对象是解析一个文本文件得到结果，文档对象里面包含：
 *              文档id.
 *              文档的绝对路径.
 *              文档包含的三元组对象列表，一个三元组对象是抽象类AbstractTermTuple的子类实例
 *</pre>
 */

public class Document extends AbstractDocument {
    /**
     * 缺省构造函数
     */
    public Document(){}

    /**
     * 构造函数
     * @param docId：文档id
     * @param docPath：文档绝对路径
     */
    public Document(int docId, String docPath){
        super(docId, docPath);
    }

    /**
     * 构造函数
     * @param docId：文档id
     * @param docPath：文档绝对路径
     * @param tuples: 可选的TermTuple List
     */
    public Document(int docId, String docPath,List<AbstractTermTuple> tuples){
        super(docId, docPath, tuples);
    }

    /**
     * 获得文档id
     * @return ：文档id
     */
    @Override
    public int getDocId() {
        return docId;
    }


    /**
     * 设置文档id
     * @param docId：文档id
     */
    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    /**
     * 获得文档绝对路径
     * @return ：文档绝对路径
     */
    @Override
    public String getDocPath() {
        return docPath;
    }


    /**
     * 设置文档绝对路径
     * @param docPath：文档绝对路径
     */
    @Override
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }


    /**
     * 获得文档包含的三元组列表
     * @return ：文档包含的三元组列表
     */
    @Override
    public List<AbstractTermTuple> getTuples() {
        return tuples;
    }


    /**
     * 向文档对象里添加三元组, 要求不能有内容重复的三元组，此处使用contains方法来判断是否有重复值
     * @param tuple ：要添加的三元组
     */
    @Override
    public void addTuple(AbstractTermTuple tuple) {
        // 避免重复值
        if (contains(tuple)) {
            return;
        }
        tuples.add(tuple);
    }



    /**
     * 判断是否包含指定的三元组
     * @param tuple ： 指定的三元组
     * @return ： 如果包含指定的三元组，返回true;否则返回false
     * 可以简单的使用成员tuple作为一个List所具有的contains方法来判断
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return tuples.contains(tuple);
    }

    /**
     * 获得文档包含的三元组列表
     * @return ：文档包含的三元组列表
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        return tuples.get(index);
    }

    /**
     * 获得文档包含的三元组列表
     * @return ：文档包含的三元组列表
     */
    @Override
    public int getTupleSize() {
        return tuples.size();
    }



    /**
     * 获得Document的字符串表示
     * @return ： Document的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (AbstractTermTuple tuple : tuples) {
            builder.append(tuple.toString());
        }
        return "{ docId: " + docId + ", docPath: " + docPath + ", tuples: \n" + builder.toString() + "} \n";
    }
}
