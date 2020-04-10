package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IndexSearcher extends AbstractIndexSearcher {
    @Override
    public void open(String indexFile) {
        this.index = new Index();
        index.load(new File(indexFile));
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        // 忽略大小写
        if (Config.IGNORE_CASE) {
            queryTerm.setContent(queryTerm.getContent().toLowerCase());
        }
        AbstractPostingList indexSearchResult = index.search(queryTerm);
        if (indexSearchResult == null) {
            return new Hit[0];
        }
        List<AbstractHit> result = new ArrayList<>();
        for (int i = 0; i < indexSearchResult.size(); i++) {
            AbstractPosting posting = indexSearchResult.get(i);
            AbstractHit hit = new Hit(posting.getDocId(), index.getDocName(posting.getDocId()));
            hit.getTermPostingMapping().put(queryTerm, posting);
            // 这个地方其实有点奇怪
            hit.setScore(sorter.score(hit));
            result.add(hit);
        }
        sorter.sort(result);
        AbstractHit[] returnResult = new AbstractHit[result.size()];
        return result.toArray(returnResult);
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList indexSearchResult1 = index.search(queryTerm1);
        AbstractPostingList indexSearchResult2 = index.search(queryTerm2);
        List<AbstractHit> result = new ArrayList<>();
        if (combine == LogicalCombination.AND) {
            // 求交集
            for (int i = 0; i < indexSearchResult1.size(); i++) {
                // 获取docId
                int docId = indexSearchResult1.get(i).getDocId();
                int sub_index = indexSearchResult2.indexOf(docId);
                if (sub_index != -1) {
                    // 如果存在的话构建一个新的Posting, 频率为两个之中的较小值,位置为两个集合的并集
                    AbstractHit hit = new Hit(docId, index.getDocName(docId));
                    // 不知道对不对
                    hit.getTermPostingMapping().put(queryTerm1, indexSearchResult1.get(i));
                    hit.getTermPostingMapping().put(queryTerm2, indexSearchResult2.get(sub_index));
                    result.add(hit);
                }
            }
        } else if (combine == LogicalCombination.OR) {
            for (int i = 0; i < indexSearchResult1.size(); i++) {
                // 首先添加
                int docId = indexSearchResult1.get(i).getDocId();
                int sub_index = indexSearchResult2.indexOf(docId);
                if (sub_index != -1) {
                    // 如果在另外一个词语中没有,那就正常添加
                    AbstractHit hit = new Hit(docId, index.getDocName(docId));
                    hit.getTermPostingMapping().put(queryTerm1, indexSearchResult1.get(i));
                    // 这个地方其实有点奇怪
                    hit.setScore(sorter.score(hit));
                    result.add(hit);
                } else {
                    // 如果在另外一个中有, 那就要做一些修改
                    AbstractHit hit = new Hit(docId, index.getDocName(docId));
                    hit.getTermPostingMapping().put(queryTerm1, indexSearchResult1.get(i));
                    hit.getTermPostingMapping().put(queryTerm2, indexSearchResult2.get(sub_index));
                    // 这个地方其实有点奇怪
                    hit.setScore(sorter.score(hit));
                    result.add(hit);
                }
            }
            for (int i = 0; i < indexSearchResult2.size(); i++) {
                int docId = indexSearchResult2.get(i).getDocId();
                int sub_index = indexSearchResult1.indexOf(docId);
                if (sub_index == -1) {
                    // 只有当1中不存在的时候才添加
                    AbstractHit hit = new Hit(docId, index.getDocName(docId));
                    hit.getTermPostingMapping().put(queryTerm2, indexSearchResult2.get(i));
                    // 这个地方其实有点奇怪
                    hit.setScore(sorter.score(hit));
                    result.add(hit);
                }
            }
        }

        sorter.sort(result);
        AbstractHit[] returnResult = new AbstractHit[result.size()];
        return result.toArray(returnResult);
    }
}
