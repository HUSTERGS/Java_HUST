package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IndexBuilder extends AbstractIndexBuilder {
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

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
