class Hoge {
  def foo(): Bar = {
    for {
      a <- func1
      b <- func2
    } yield Bar(a, b)
  }
}
