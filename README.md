SQL Statement Builder
===================================

This project is a SQL statement builder, allow you write SQL in Java.  
It's not a fast way to build SQL statement, but it may make codes more intuitive.

### 0. Example
The java code is:

```java
Column fruit_name = new Column("name");
SQL.delete("fruit", Clause.equal(fruit_name, "apple"));
```
The output sql is:

```sql
DELETE FROM `fruit` WHERE `name` = "apple";
```

### 1. INSERT
```java
String sql = new Insert(String table)
		.addAll(Map<String, Object> cv)          // by map<column, value>
		.add(String column, Object value)        // by values
		.toString();
```


### 2. DELETE
```java
SQL.delete(String table, Clause where);
```

### 3. UPDATE
```java
String sql = new Update(String table)
		.addAll(Map<String, Object> cv)          // by map<column, value>
		.add(String column, Object value)        // by values
		.where(clause)
		.toString();
```

### 4. SELECT
Simple way to select all colunm in one table.

```java
SQL.select(String table, Clause where, int limit);
```

Most of time you may use the select builder for complex query.  
You're no need to input the table name for the Select, but the Column `MUST` be constructed with table name, especially in multi-table queries!

```java
Column price_fruitId = new Column("price", "fruitId");
Column fruit_fruitId = new Column("fruit", "fruitId");
Column fruit_name = new Column("fruit", "name");

String sql = new Select()
		.addColumn(new Column("fruit", "*"))     // all column in `fruit`
		.addColumn(new Column("price", "rmb"))   // the column in `price`
		.innerJoin(price_fruitId, fruit_fruitId) // join
		.where(Clause.like(fruit_name, "apple")) // where
		.orderBy(new Asc(price_fruitId))         // order by
		.limit(2)                                // limit
		.toString();
```

### 5. WHERE
```java
// Sigle clause:
//      in, between, isNull, isNotNull, like,
//      equal(=), lessThan(<), lessEqual(<=), moreThan(>), moreEqual(>=)
Clause.equal(column, pattern)

// Multi-clause
MultiClause clause = new MultiClause(clause1)
		.and(clause2)
		.or(new MultiClause(xxx)...);
```
### 6. Column
```java
Column(String name);                                // no table
Column(String table, String name);                  // multi-table query
Column(table, name).as(String alias);               // with column alias
Column(table, name).as(tableAlias, nameAlias);      // with table alias
```
