package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StopWords;

import java.io.IOException;
import java.util.Arrays;

public class TermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public TermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() throws IOException {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null) {
            return null;
        }
        while (Arrays.asList(StopWords.STOP_WORDS).contains(termTuple.term.getContent()) |
                termTuple.term.getContent().length() > Config.TERM_FILTER_MAXLENGTH |
                termTuple.term.getContent().length() < Config.TERM_FILTER_MINLENGTH |
                !termTuple.term.getContent().matches(Config.TERM_FILTER_PATTERN)) {
            /*
            * 如果是停用词
            * 或者长度大于最大长度
            * 或者长度小于最小长度
            * 或者不是全为字母
            * 则进行过滤
            * */
            termTuple = input.next();
            if (termTuple == null) {
                return null;
            }
        }
        return termTuple;
    }
}
