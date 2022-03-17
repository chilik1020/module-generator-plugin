package model

const val DEFAULT_PACKAGE = "by.priorbank"
const val KMM_DEFAULT_PACKAGE = "by.priorbank.shared"
const val KMM_SUB_MODULE_DEFAULT_NAME_TEMPLATE = "kmm-pi-features"
const val GATEWAY_POSTFIX = "gateway"
const val DOMAIN_POSTFIX = "domain"
const val PRESENTATION_POSTFIX = "presentation"

const val GRADLE_BUILD_FILE_NAME = "build"
const val PRO_GUARD_FILE_NAME = "proguard-rules"
const val GIT_IGNORE_FILE_NAME = ""
const val ANDROID_MANIFEST_FILE_NAME = "AndroidManifest"

const val PRO_GUARD_TEMPLATE = "#"
const val GIT_IGNORE_TEMPLATE = "/build"

const val FOLDER_SRC_NAME = "src"
const val FOLDER_RES_NAME = "res"
const val FOLDER_MAIN_NAME = "main"
const val FOLDER_KOTLIN_NAME = "kotlin"
const val FOLDER_ANDROID_MAIN_NAME = "androidMain"
const val FOLDER_COMMON_MAIN_NAME = "commonMain"
const val FOLDER_IOS_MAIN_NAME = "iosMain"
const val FOLDER_ASSETS_NAME = "assets"

const val INCLUDE = "include"

val MANIFEST_TEMPLATE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
    "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
    "    package=\"${Variable.PACKAGE_NAME.value}\">\n" +
    "\n" +
    "</manifest>"

const val DEFAULT_GRADLE_TEMPLATE = "plugins {\n" +
    "    id 'com.android.library'\n" +
    "    id 'kotlin-android'\n" +
    "}\n" +
    "\n" +
    "android {\n" +
    "    compileSdkVersion 30\n" +
    "    buildToolsVersion \"30.0.3\"\n" +
    "\n" +
    "    defaultConfig {\n" +
    "        minSdkVersion 21\n" +
    "        targetSdkVersion 30\n" +
    "        versionCode 1\n" +
    "        versionName \"1.0\"\n" +
    "\n" +
    "        testInstrumentationRunner \"androidx.test.runner.AndroidJUnitRunner\"\n" +
    "        consumerProguardFiles \"consumer-rules.pro\"\n" +
    "    }\n" +
    "\n" +
    "    buildTypes {\n" +
    "        release {\n" +
    "            minifyEnabled false\n" +
    "            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
    "        }\n" +
    "    }\n" +
    "    compileOptions {\n" +
    "        sourceCompatibility JavaVersion.VERSION_1_8\n" +
    "        targetCompatibility JavaVersion.VERSION_1_8\n" +
    "    }\n" +
    "    kotlinOptions {\n" +
    "        jvmTarget = '1.8'\n" +
    "    }\n" +
    "    buildFeatures {\n" +
    "        viewBinding = true\n" +
    "    }\n" +
    "}\n" +
    "\n" +
    "dependencies {\n" +
    "}"

const val DOMAIN_GRADLE_TEMPLATE = "plugins {\n" +
    "    id 'com.android.library'\n" +
    "    id 'kotlin-android'\n" +
    "}\n" +
    "\n" +
    "android {\n" +
    "    compileSdkVersion 30\n" +
    "    buildToolsVersion \"30.0.3\"\n" +
    "\n" +
    "    defaultConfig {\n" +
    "        minSdkVersion 21\n" +
    "        targetSdkVersion 30\n" +
    "        versionCode 1\n" +
    "        versionName \"1.0\"\n" +
    "\n" +
    "        testInstrumentationRunner \"androidx.test.runner.AndroidJUnitRunner\"\n" +
    "        consumerProguardFiles \"consumer-rules.pro\"\n" +
    "    }\n" +
    "\n" +
    "    buildTypes {\n" +
    "        release {\n" +
    "            minifyEnabled false\n" +
    "            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
    "        }\n" +
    "    }\n" +
    "    compileOptions {\n" +
    "        sourceCompatibility JavaVersion.VERSION_1_8\n" +
    "        targetCompatibility JavaVersion.VERSION_1_8\n" +
    "    }\n" +
    "    kotlinOptions {\n" +
    "        jvmTarget = '1.8'\n" +
    "    }\n" +
    "    buildFeatures {\n" +
    "        viewBinding = true\n" +
    "    }\n" +
    "}\n" +
    "\n" +
    "dependencies {\n" +
    "}"

const val PRESENTATION_GRADLE_TEMPLATE = "plugins {\n" +
    "    id 'com.android.library'\n" +
    "    id 'kotlin-android'\n" +
    "}\n" +
    "\n" +
    "android {\n" +
    "    compileSdkVersion 30\n" +
    "    buildToolsVersion \"30.0.3\"\n" +
    "\n" +
    "    defaultConfig {\n" +
    "        minSdkVersion 21\n" +
    "        targetSdkVersion 30\n" +
    "        versionCode 1\n" +
    "        versionName \"1.0\"\n" +
    "\n" +
    "        testInstrumentationRunner \"androidx.test.runner.AndroidJUnitRunner\"\n" +
    "        consumerProguardFiles \"consumer-rules.pro\"\n" +
    "    }\n" +
    "\n" +
    "    buildTypes {\n" +
    "        release {\n" +
    "            minifyEnabled false\n" +
    "            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
    "        }\n" +
    "    }\n" +
    "    compileOptions {\n" +
    "        sourceCompatibility JavaVersion.VERSION_1_8\n" +
    "        targetCompatibility JavaVersion.VERSION_1_8\n" +
    "    }\n" +
    "    kotlinOptions {\n" +
    "        jvmTarget = '1.8'\n" +
    "    }\n" +
    "    buildFeatures {\n" +
    "        viewBinding = true\n" +
    "    }\n" +
    "}\n" +
    "\n" +
    "dependencies {\n" +
    "}"

const val DEFAULT_GRADLE_DSL_TEMPLATE = "plugins {\n" +
    "    kotlin(\"multiplatform\")\n" +
    "    id(\"com.android.library\")\n" +
    "}\n" +
    "\n" +
    "kotlin {\n" +
    "    android()\n" +
    "    ios()\n" +
    "\n" +
    "    sourceSets {\n" +
    "        val commonMain by getting {\n" +
    "            dependencies {\n" +
    "                implementation(project(Dependencies.Modules.Shared.Core.Api.Common.MODULE))\n" +
    "            }\n" +
    "        }\n" +
    "        val commonTest by getting {\n" +
    "            dependencies {\n" +
    "                implementation(kotlin(\"test-common\"))\n" +
    "                implementation(kotlin(\"test-annotations-common\"))\n" +
    "            }\n" +
    "        }\n" +
    "        val androidMain by getting\n" +
    "        val androidTest by getting {\n" +
    "            dependencies {\n" +
    "                implementation(kotlin(\"test-junit\"))\n" +
    "                implementation(\"junit:junit:4.13.2\")\n" +
    "            }\n" +
    "        }\n" +
    "        val iosMain by getting\n" +
    "        val iosTest by getting\n" +
    "    }\n" +
    "}\n" +
    "\n" +
    "android {\n" +
    "    compileSdkVersion(30)\n" +
    "    sourceSets[\"main\"].manifest.srcFile(\"src/androidMain/AndroidManifest.xml\")\n" +
    "    defaultConfig {\n" +
    "        minSdkVersion(21)\n" +
    "        targetSdkVersion(30)\n" +
    "    }\n" +
    "}"