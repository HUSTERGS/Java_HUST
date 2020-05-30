package hust.cs.javacourse.search.index.impl;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.*;

import java.io.*;


/**
 * <pre>
 * DocumentBuilder是Document构造器的具体子类.
 *      Document构造器的功能应该是由解析文本文档得到的TermTupleStream，产生Document对象.
 *  主要实现了父类的两个重载build方法
 * </pre>
 */
public class DocumentBuilder extends AbstractDocumentBuilder {

    /**
     * <pre>
     * 由解析文本文档得到的TermTupleStream,构造Document对象.
     * @param docId             : 文档id
     * @param docPath           : 文档绝对路径
     * @param termTupleStream   : 文档对应的TermTupleStream
     * @return ：Document对象
     * </pre>
     */
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) throws IOException {
        AbstractDocument document = new Document(docId, docPath);
        AbstractTermTuple termTuple = termTupleStream.next();
        while (termTuple != null) {
            document.addTuple(termTuple);
            termTuple = termTupleStream.next();
        }
        termTupleStream.close();
        return document;
    }



    /**
     * <pre>
     * 由给定的File,构造Document对象.
     * 该方法利用输入参数file构造出AbstractTermTupleStream子类对象后,内部调用
     *      AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream)
     * @param docId     : 文档id
     * @param docPath   : 文档绝对路径
     * @param file      : 文档对应File对象
     * @return          : Document对象
     * </pre>
     */
    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        AbstractDocument document = null;
        AbstractTermTupleStream ts = null;
        try {
            ts = new TermTupleScanner(new BufferedReader(new InputStreamReader(new
                    FileInputStream(file))));
            ts = new StopWordTermTupleFilter(ts); //再加上停用词过滤器
            ts = new PatternTermTupleFilter(ts); //再加上正则表达式过滤器
            ts = new LengthTermTupleFilter(ts); //再加上单词长度过滤器
            document = build(docId, docPath, ts);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ts.close();
        }
        return document;
    }
}
