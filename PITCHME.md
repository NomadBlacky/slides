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

## 「読みやすい」コードとはなんだろう?

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

これらの要素はScalaも同様に役立ちます。

+++

今回は「Scala関西Summit」ということなので…

Scalaの言語機能にフォーカスして  
Scalaらしく、「読みやすい」コードを書けることを目指していきましょう。

---

## パターンマッチ編

+++

Before

```scala
val b1: Boolean = true
val b2: Boolean = false
if (b1) {
  if (b2) {
    funcA()
  } else {
    funcB()
  }
} else {
  if (b2) {
    funcC()
  } else {
    funcD()
  }
}
```

+++

After

```scala
val b1: Boolean = true
val b2: Boolean = false
(b1, b2) match {
  case (true, true)   => funcA()
  case (true, false)  => funcB()
  case (false, true)  => funcC()
  case (false, false) => funcD()
}
```

### 色々なところでパターンマッチ

+++

```scala
```

値の初期化

+++

```scala
```

PartialFunction (部分関数)

---

## コレクション操作編

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

## まとめ

「読みやすい」Scalaコードを書くために

+ 関数を小さく保つ・書式を揃える、といった要素はScalaでも同様に役立つ
+ Scalaの言語機能を理解しよう
+ 適切なAPIを探そう、使おう

Scalaで実装する際の助けになれば幸いです
