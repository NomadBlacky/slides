# Readable Code in Scala

## Scala関西Summit2018

門脇 拓巳 (@blac_k_ey)

---

## 自己紹介

+ 門脇 拓巳 (かどわき たくみ)
+ 所属: 株式会社セプテーニ・オリジナル
+ GitHub: [NomadBlacky](https://github.com/NomadBlacky)
+ Twitter: [@blac_k_ey](https://twitter.com/blac_k_ey)

---

## 発表内容について

Scalaで読みやすいコードを書くためのTipsをご紹介します。
 
+ ある程度Scalaの基本文法に慣れてきた
+ Scalaらしいコードを書けているか不安な人

…といった、初心者の方を対象としています。

---

## 「読みやすい」コードとはなんだろう?

---

「読みにくい」コードから考える

+ ひと目で仕様を把握できない
+ 適切な機能・APIを使って実装されていない
+ ドキュメントが整備されていない
+ 複雑
+ 書式が統一されていない

+++

「読みやすい」コード

+ ひと目で仕様が分かる
+ 適切な機能・APIを使って実装されている
+ ドキュメントが整備されている
+ シンプル
+ 書式が統一されている

+++

Scalaに限らず、読みやすいコードを書くためにできること

+ 関数(メソッド)を小さく保つ
+ 命名規則
+ 適切な機能・APIを使う
+ ネストを深くしない
+ ドキュメントを書く
+ 副作用を起こすところを局所化する
+ ...

これらの要素はScalaも同様に役立ちます。

+++

今回は「Scala関西Summit」ということなので…

+ 適切な機能・APIを使って実装されている
+ シンプル

特にこの2つに対し、Scalaの言語機能を有効に使うことで、  
Scalaらしく、「読みやすい」コードを書けることを目指していきましょう。

---

## パターンマッチ編

+++

まず、以下の条件を満たす関数を実装することを考えます。

```scala
case class User(name: Option[String], isActive: Boolean)
def extractUserNameWithTop10Chars(users: List[User]): List[String] = ???
```

+ List[User]クラスに対して
+ isActiveがtrueのものだけを抜き出し
+ 名前が10文字以上の場合は最初の10文字だけを抜き出し
+ List[String]を返す

+++

```scala
def extractUserNameWithTop10Chars(users: List[User]): List[String] = {
  users
    .withFilter(u => u.isActive)
    .withFilter(u => u.name.isDefined)
    .map { user =>
      if (10 <= user.name.get.length) {
        user.name.get.take(10)
      } else {
        user.name.get
      }
    }
}
```

素直に実装してみる

+++

```scala
def extractUserNameWithTop10Chars02(users: List[User]): List[String] = {
  users
    .withFilter(u => u.isActive)
    .withFilter(u => u.name.isDefined)
    .map { user =>
      user match {
        case User(Some(name), _) if 10 <= name.length => name.take(10)
        case User(Some(name), _)                      => name
      }
    }
}
```

パターンマッチを使う

+++

```scala
def extractUserNameWithTop10Chars03(users: List[User]): List[String] = {
  users.flatMap { user =>
    user match {
      case User(Some(name), true) if 10 <= name.length => Some(name.take(10))
      case User(Some(name), true)                      => Some(name)
      case _                                           => None
    }
  }
}
```

もっとパターンマッチを使う

+++

```scala
def extractUserNameWithTop10Chars04(users: List[User]): List[String] =
  users.collect {
    case User(Some(name), true) if 10 <= name.length => name.take(10)
    case User(Some(name), true)                      => name
  }
```

collectを使う

+++

```scala
final override def collect[B, That](pf: PartialFunction[A, B])
  (implicit bf: CanBuildFrom[List[A], B, That]): That
```

ところで、PartialFunctionって何でしょうか?

+++

```scala
trait PartialFunction[-A, +B] extends (A => B)
```

+ 「部分関数」とも言われる
+ 特定の引数に対してのみ結果を返す関数。
+ 引数により値を返さない場合がある。

+++

```scala
val pf: PartialFunction[Int, String] = {
  case 0 => "zero"
  case i if i % 2 == 0 => "even"
}

// isDefinedAt ... 値をを返すか調べる
pf.isDefinedAt(0) shouldBe true
pf.isDefinedAt(1) shouldBe false
pf.isDefinedAt(2) shouldBe true

// lift ... 結果をOptionに包む
pf.lift(0) shouldBe Some("zero")
pf.lift(1) shouldBe None
pf.lift(2) shouldBe Some("even")
```

+++

### 標準ライブラリにおけるPartialFunctionの利用例

+++

```scala
// find と map
def isActiveUser(username: String): Option[Boolean] =
  userList.find(_.name.contains(username)).map(_.isActive)

// collectFirst
def isActiveUser2(username: String): Option[Boolean] =
  userList.collectFirst {
    case User(Some(name), isActive) if name == username => isActive
  }
```

`TraversableOnce#collectFirst`

+++

```scala
def storeUser(user: User): Try[Unit] = Try {
  if (user.isActive) println("stored") else throw new IOException
}
def storeError(t: Throwable): Try[Unit] = Try(throw new IllegalStateException)

def tryStoringUser(user: User): Try[Unit] = {
  storeUser(user) match {
    case Success(_) => Success(())
    case Failure(e: IOException) => storeError(e)
    case Failure(e) => Failure(e)
  }
}

def tryStoringUser2(user: User): Try[Unit] =
  storeUser(user).recoverWith {
    case e: IOException => storeError(e)
  }
```

`Try#recoverWith`

---

## for式編

+++

```scala
```

for式のおさらい

---

## Future編

---

## コードスタイル編

+++

[Effective Scala](http://twitter.github.io/effectivescala/index-ja.html#書式)

> スタイルに本質的な良し悪しはないし、個人的な好みはほぼ人によって異なる。
> しかし、同じ整形ルールを一貫して適用すれば、ほとんどの場合で可読性が高まる。

+++



+++

宣伝

sbtプロジェクトにscalafmtをコマンド一発で導入する

[septeni-original/scalafmt-config](https://github.com/septeni-original/scalafmt-config)

`curl -L https://git.io/vdiNA | bash`

---

## 最後に

+++

今回紹介したコードは以下に置いてあります。

[NomadBlacky/scala_samples](https://github.com/NomadBlacky/scala_samples)

今回の内容以外にも、Scala関する様々なサンプルコードがあります。  
Scala学習の手助けとなれば幸いです。

+++

![scala-book](images/zissen-scala.jpg)

「実践Scala入門」発売中!  
レビューを少しお手伝いさせていただきました🙇

8章、9章はよりScalaらしく書くための手助けになります。

---

## まとめ

「読みやすい」Scalaコードを書くために

+ 関数を小さく保つ・書式を揃える、といった要素はScalaでも同様に役立つ
+ Scalaの言語機能を理解しよう、使ってみよう
+ 適切なAPIを探そう、使おう

Scalaで実装する際の助けになれば幸いです🙇
