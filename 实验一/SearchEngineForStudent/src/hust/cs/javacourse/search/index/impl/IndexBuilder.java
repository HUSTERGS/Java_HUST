package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;



/**
 * <pre>
 * AbstractIndexBuilder的具体实现类
 * </pre>
 */
public class IndexBuilder extends AbstractIndexBuilder {
    /**
     * 有参数构造函数
     * @param docBuilder 接收的DocumentBuilder
     */
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }


    /**
     * <pre>
     * 构建指定目录下的所有文本文件的倒排索引.
     *      需要遍历和解析目录下的每个文本文件, 得到对应的Document对象，再依次加入到索引，并将索引保存到文件.
     * @param rootDirectory ：指定目录
     * @return ：构建好的索引
     * </pre>
     */
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        AbstractIndex index = new Index();
        List<String> filePaths = FileUtil.list(rootDirectory);
        // 对文件名排一下序
        filePaths.sort(String::compareTo);
        for (String docPath : filePaths) {
            AbstractDocument document = null;
            try {
                document = docBuilder.build(docId, docPath, new File(docPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            index.addDocument(document);
            docId += 1;
        }
        return index;
    }
}
