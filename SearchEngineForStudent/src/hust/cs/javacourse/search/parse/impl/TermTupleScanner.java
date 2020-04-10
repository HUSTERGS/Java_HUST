package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class TermTupleScanner extends AbstractTermTupleScanner {

    public TermTupleScanner(BufferedReader input) {
        super(input);
    }

    // 用于缓存多余的数据
    Queue<AbstractTermTuple> inputBuffer = new LinkedList<>();

    int pos = 0;

    @Override
    public AbstractTermTuple next() throws IOException {
        if (inputBuffer.isEmpty()) {
            String string = input.readLine();
            // 如果读完了，直接返回null
            if (string == null) {
                return null;
            }
            while (string.trim().length() == 0) {
                // 当遇到空行的时候，就一直读，直到遇到非空行或者文件末尾
                string = input.readLine();
                if (string == null) {
                    return null;
                }
            }
            StringSplitter splitter = new StringSplitter();
            splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
            for (String word : splitter.splitByRegex(string)) {
                TermTuple tt = new TermTuple();
                tt.curPos = pos;
                // 是否忽略大小写
                if (Config.IGNORE_CASE) {
                    tt.term = new Term(word.toLowerCase());
                } else {
                    tt.term = new Term(word);
                }
                inputBuffer.add(tt);
                pos ++;
            }
        }
        return inputBuffer.poll();
    }
}
