package com.baidu.duer.lajifl.bean.lajifl;

/**
 * 垃圾类型描述
 */
public class Description {
    // 垃圾类型解释
    private String Concept;
    // 垃圾类型包含的垃圾(举例)
    private String Including;
    // 此类垃圾的处理建议
    private String Release_requirement;

    public String getConcept() {
        return Concept;
    }

    public void setConcept(String concept) {
        Concept = concept;
    }

    public String getIncluding() {
        return Including;
    }

    public void setIncluding(String including) {
        Including = including;
    }

    public String getRelease_requirement() {
        return Release_requirement;
    }

    public void setRelease_requirement(String release_requirement) {
        Release_requirement = release_requirement;
    }

    @Override
    public String toString() {
        return "Description{" +
                "Concept='" + Concept + '\'' +
                ", Including='" + Including + '\'' +
                ", Release_requirement='" + Release_requirement + '\'' +
                '}';
    }
}
