package model

const val DEFAULT_PACKAGE = "by.priorbank"

const val GRADLE_BUILD_FILE_NAME = "build"
const val PRO_GUARD_FILE_NAME = "proguard-rules"
const val GIT_IGNORE_FILE_NAME = ""
const val ANDROID_MANIFEST_FILE_NAME = "AndroidManifest"

const val PRO_GUARD_TEMPLATE = "#"
const val GIT_IGNORE_TEMPLATE = "/build"

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