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
    public AbstractIndex buildIndex(String rootDirectory) throws IOException {
        AbstractIndex index = new Index();
        List<String> filePaths = FileUtil.list(rootDirectory);
        filePaths.sort(String::compareTo);
        for (String docPath : filePaths) {
            AbstractDocument document = new DocumentBuilder().build(docId, docPath, new File(docPath));
            index.addDocument(document);
            docId += 1;
        }
        return index;
    }
}
