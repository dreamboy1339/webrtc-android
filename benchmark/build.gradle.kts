/*
 * Copyright 2023 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.getstream.Configurations

plugins {
  id("com.android.test")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "io.getstream.webrtc.android.benchmark"
  compileSdk = Configurations.compileSdk

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  defaultConfig {
    minSdk = 24
    targetSdk = Configurations.targetSdk
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    // This benchmark buildType is used for benchmarking, and should function like your
    // release build (for example, with minification on). It"s signed with a debug key
    // for easy local/CI testing.
    create("benchmark") {
      isDebuggable = true
      signingConfig = getByName("debug").signingConfig
      matchingFallbacks += listOf("release")
    }
  }

  targetProjectPath = ":app"
  experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
  implementation(libs.androidx.test.runner)
  implementation(libs.androidx.test.uiautomator)
  implementation(libs.androidx.benchmark.macro)
  implementation(libs.androidx.profileinstaller)
}

androidComponents {
  beforeVariants(selector().all()) {
    it.enable = it.buildType == "benchmark"
  }
}