package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.Objects;


/**
 * AbstractTermTuple的具体实现类
 */
public class TermTuple extends AbstractTermTuple {

    /**
     * 判断二个三元组内容是否相同
     * @param obj ：要比较的另外一个三元组
     * @return 如果内容相等（三个属性内容都相等）返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermTuple) {
            return this.curPos == ((TermTuple) obj).curPos &&
                    Objects.equals(this.term, ((TermTuple) obj).term);
        }
        return false;
    }

    /**
     * 获得三元组的字符串表示
     * @return ： 三元组的字符串表示
     */
    @Override
    public String toString() {
        return "{Term: " + term +
                ", CurPos: " + curPos +
                ", Freq: " + freq + " }\n";
    }
}
