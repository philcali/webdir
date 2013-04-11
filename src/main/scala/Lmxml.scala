package com.github.philcali.web

import lmxml.{ LmxmlFactory, ResourceLoading }
import lmxml.PlainLmxmlParser
import lmxml.shortcuts.html.HtmlShortcuts

object Lmxml extends LmxmlFactory with ResourceLoading {
  def createParser(step: Int) =
    new PlainLmxmlParser(step) with HtmlShortcuts
}
