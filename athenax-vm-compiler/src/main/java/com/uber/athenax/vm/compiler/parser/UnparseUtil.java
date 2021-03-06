/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uber.athenax.vm.compiler.parser;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlWriter;

import java.util.Arrays;

class UnparseUtil {
  private final SqlWriter writer;
  private final int leftPrec;
  private final int rightPrec;

  UnparseUtil(SqlWriter writer, int leftPrec, int rightPrec) {
    this.writer = writer;
    this.leftPrec = leftPrec;
    this.rightPrec = rightPrec;
  }

  UnparseUtil keyword(String... keywords) {
    Arrays.stream(keywords).forEach(writer::keyword);
    return this;
  }

  UnparseUtil node(SqlNode n) {
    n.unparse(writer, leftPrec, rightPrec);
    return this;
  }

  UnparseUtil nodeList(SqlNodeList l) {
    writer.keyword("(");
    if (l.size() > 0) {
      l.get(0).unparse(writer, leftPrec, rightPrec);
      for (int i = 1; i < l.size(); ++i) {
        writer.keyword(",");
        l.get(i).unparse(writer, leftPrec, rightPrec);
      }
    }
    writer.keyword(")");
    return this;
  }
}
