SQL Statement Builder
===================================
[English](README.md) | 中文

这个项目只是一个Java编写的SQL语句生成器。   

它并不能让提高你的代码执行效率，但是可以让你的Java代码更直观，更纯粹。

### 0. 例子
这个一个简单的例子，像这样:

```java
Column fruit_name = new Column("name");
SQL.delete("fruit", Clause.equal(fruit_name, "apple"));
```
通过上面生成的SQL语句:

```sql
DELETE FROM `fruit` WHERE `name` = "apple";
```

### 1. 插入（INSERT）
```java
String sql = new Insert(String table)
		.addAll(Map<String, Object> cv)          // map<列, 值>批量全部
		.add(String column, Object value)        // 单独添加
		.toString();
```


### 2. 删除（DELETE）
```java
SQL.delete(String table, Clause where);
```

### 3. 修改（UPDATE）
```java
String sql = new Update(String table)
		.addAll(Map<String, Object> cv)          // 批量全部
		.add(String column, Object value)        // 单独添加
		.where(clause)
		.toString();
```

### 4. 查询（SELECT）
简单地搜索全表：

```java
SQL.select(String table, Clause where, int limit);
```

大多数情况，我们需要使用 select builder 来帮助我们事先复杂搜索。  

使用builder不需要单独声明表名，但是所有包含的列的表名都`必须`不能为空，尤其是在多表查询的时候！

```java
Column price_fruitId = new Column("price", "fruitId");
Column fruit_fruitId = new Column("fruit", "fruitId");
Column fruit_name = new Column("fruit", "name");

String sql = new Select()
		.addColumn(new Column("mylike", null))     // 没有搜索列
		.addColumn(new Column("fruit", "*"))       // 表的所有列
		.addColumn(new Column("price", xxx))       // 表的指定列
		.innerJoin(price_fruitId, fruit_fruitId)   // join
		.where(Clause.like(fruit_name, "apple"))   // where
		.orderBy(new Asc(price_fruitId).desc(xxx)) // order by
		.limit(2)                                  // limit
		.toString();
```

### 5. 约束（WHERE）
```java
// 单独条件：
//      in, between, isNull, isNotNull, like,
//      equal(=), lessThan(<), lessEqual(<=), moreThan(>), moreEqual(>=)
Clause.equal(column, pattern)

// 多条件
MultiClause clause = new MultiClause(clause1)
		.and(clause2)
		.or(new MultiClause(xxx)...);
```
### 6. 列（Column）
```java
Column(String name);                              // 不带表名
Column(String table, String name);                // 带表名
Column(table, name).as(String alias);             // 带列的“别名”
Column(table, name).as(tableAlias, nameAlias);    // 带表的“别名”
```
