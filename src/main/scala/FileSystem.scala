package com.github.philcali.web

import java.io.File

trait FileSystem {
  val cwd: File

  object FileCheck {
    def unapply(path: String) = {
      val file = new File(cwd, path)
      if (file.exists && file.isDirectory)
        Some(file) else None
    }
  }
}
