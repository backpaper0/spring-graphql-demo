# spring-graphql-demo

## 動作確認

まずは起動する。

```
mvn spring-boot:run
```

`curl`(と`jq`)で動作確認してみる。

```
$ curl -s localhost:8080/graphql -XPOST -H "Content-Type: application/json" -d '{"query":"{hello}"}' | jq
{
  "data": {
    "hello": "Hello, world!"
  }
}
```

`spring.graphql.schema.printer.enabled=true`を設定していると http://localhost:8080/graphql/schema でスキーマを確認できる。

`spring.graphql.graphiql.enabled=true`を設定していると http://localhost:8080/graphiql でクエリエディター(GraphiQL)を使える。

![](src/misc/graphiql.png)

## コードに関するメモ

スキーマ定義ファイルは`src/main/resources/graphql/schema.graphqls`。
data fetchingの定義は`src/main/java/com/example/HelloWorldDataWiring.java`で行っている。

Web MVCで使う場合は`org.springframework.graphql.boot.GraphQlWebMvcAutoConfiguration`で自動設定されるみたい。

プロパティは`org.springframework.graphql.boot.GraphQlProperties`を見れば把握できそう。

### graphql.schema.DataFetcher

`graphql.schema.DataFetcher`は`java.util.Optional`を返しても良いっぽい(`BookDataWiring`と`BookRepository`あたりを参照)。

`graphql.schema.DataFetchingEnvironment`から`getSource`で現在処理している`type`を取得したり`getArgument`でクエリーパラメーターを取得して子`type`を取得することができる。

## 参考リソース

- https://github.com/spring-projects/spring-graphql
- https://spring.io/blog/2021/07/06/hello-spring-graphql

