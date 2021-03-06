/**
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
package org.apache.avro.compiler;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.avro.AvroTestUtil;
import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;
import org.apache.avro.generic.GenericData.StringType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestSpecificCompiler {

  @Test
  public void testCanReadTemplateFilesOnTheFilesystem() throws IOException, URISyntaxException{
    String schemaSrcPath = "src/test/resources/simple_record.avsc";
    String velocityTemplateDir = "src/main/velocity/org/apache/avro/compiler/specific/templates/java/classic/";
    File src = new File(schemaSrcPath);
    Schema.Parser parser = new Schema.Parser();
    Schema schema = parser.parse(src);
    SpecificCompiler compiler = new SpecificCompiler(schema);
    compiler.setTemplateDir(velocityTemplateDir);
    compiler.setStringType(StringType.CharSequence);
    File outputDir = AvroTestUtil.tempDirectory(getClass(), "specific-output");
    compiler.compileToDestination(src, outputDir);
    assertTrue(new File(outputDir, "SimpleRecord.java").exists());
  }
}
