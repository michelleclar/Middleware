package elasticsearch.action.search;

public enum Query {
    query,
    name,
    match,
    match_phrase, //匹配确定的词语
    multi_match, //在多个字段上查询
    range, //范围查询
    terms, //精确词条匹配
    exists, //字段存在性查询
    prefix, //前缀查询
    wildcard, //通配符查询
    regexp, //正则表达式查询
    fuzzy, //模糊查询
    constant_score, //常量评分查询
    bool, //组合查询,可以组合多个其他查询
    boosting, //提升查询权重
}
