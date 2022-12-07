## Android Source Code

### 关于

#### Android堆栈

<img src="https://source.android.google.cn/static/docs/setup/about/images/android_framework_details.png" alt="Android 堆栈" style="zoom: 67%;" />	

Android 10 API 29 

#### build ID 

格式：**PVBB.YYMMDD.bbb[.Cn]** 

- **P** 表示平台版本代号的第一个字母，例如 **O** 表示 Oreo。
- **V** 表示支持的类别。按照惯例，**P** 表示主要平台分支。
- **BB** 是由字母和数字组成的代码，Google 可通过该代码识别 build 所属的确切代码分支。
- **YYMMDD** 表示相应版本从开发分支细分出来或与开发分支同步的日期。它并不一定是 build 的确切构建日期，因为 Google 常常会在现有 build 中增加细微的更改，并在新 build 中重复使用与现有 build 相同的日期代码。
- **bbb** 表示具有相同日期代码的不同版本，从 **001** 开始。
- **Cn** 是可选的字母数字，表示在现有 PVBB.YYMMDD.bbb build 之上构建的修补程序，从 **A1** 开始。

#### 启动fastboot模式

`adb remount`（重新挂载system分区）失败解锁

官方参考：https://source.android.google.cn/docs/setup/build/running#booting-into-fastboot-mode

repo作为git的管理工具，Gerrit提交代码（审核图形化界面）	

echo 203.208.40.66 translate.googleapis.com >> C:\Windows\System32\drivers\etc\hosts & ipconfig /flushdns > nul

repo init -u https://aosp.tuna.tsinghua.edu.cn/platform/manifest -b android-13.0.0_r15

### 开始

* 磁盘空间：如果要检出代码，至少需要 250 GB 可用磁盘空间；如果要进行构建，则还需要 150 GB。如果要进行多次构建，则需要更多空间。

- 至少需要 16 GB 的可用 RAM，但 Google 建议提供 64 GB。

从 2021 年 6 月起，Google 使用 72 核机器，内置 RAM 为 64 GB，完整构建过程大约需要 40 分钟（增量构建只需几分钟时间，具体取决于修改了哪些文件）。相比之下，RAM 数量相近的 6 核机器执行完整构建过程需要 3 个小时。

**在 Ubuntu 长期支持 (LTS) 版本中进行开发和测试**

自 2021 年 6 月 22 日起，我们将不再支持在 Windows 或 MacOS 上进行构建。

OpenJDK，Git，Python3

```shell
#设置构建输出目录
export OUT_DIR=out_mytarget
#一些具有多个存储设备的机器上，将源文件和输出存储在不同的卷上时构建速度更快。为了获得额外的性能，输出可以存储在针对速度而不是崩溃稳健性进行优化的文件系统上，因为如果文件系统损坏，所有文件都可以重新生成。
#设置它，导出OUT_DIR_COMMON_BASE变量以指向将存储输出目录的位置。
export OUT_DIR_COMMON_BASE=<path-to-your-out-directory>
#构建 Android 设备的环境
source build/envsetup.sh
#将构建目标传递给 lunch 命令
lunch aosp_cf_x86_64_phone-userdebug
#模块构建
make Settings -j8
```

[Acloud](https://android.googlesource.com/platform/tools/acloud/+/refs/heads/master/README.md)是 AOSP 中的命令行工具，可帮助用户创建虚拟 Android 设备。

Gerrit

### 下载

源代码控制工具， **Repo**（一种对 Git 构成补充的 Google 代码库管理工具）

HomePage：https://gerrit.googlesource.com/git-repo/

[Gerrit](https://gerrit-review.googlesource.com/Documentation/)是一个基于 Web 的代码审查系统，适用于使用 Git 的项目。 Gerrit 通过允许所有授权用户提交更改来鼓励更集中地使用 Git，如果他们通过代码审查，这些更改将自动合并。

Android Code Search：https://cs.android.com/



Repo使用代理

```shell
export HTTP_PROXY=http://<proxy_user_id>:<proxy_password>@<proxy_server>:<proxy_port>
export HTTPS_PROXY=http://<proxy_user_id>:<proxy_password>@<proxy_server>:<proxy_port>
```

下载期间卡住，调整堆栈设置

```shell
sudo sysctl -w net.ipv4.tcp_window_scaling=0
repo sync -j1
```

带宽不够使用本地镜像，参考：https://source.android.google.cn/docs/setup/download/downloading

设备二进制文件

对于运行带标志的 AOSP 版本分支的受支持设备，您可以从 [Google 的驱动程序](https://developers.google.cn/android/drivers)页面下载相关的官方二进制文件。

```shell
repo version 版本
repo init	初始化一个新客户端。
repo init -u https://android.googlesource.com/platform/manifest  -u指定一个清单文件
repo init -u https://android.googlesource.com/platform/manifest -b master   -b checkout 特定分支
repo sync	将客户端同步到代码库。
repo sync -c -j8 加快同步速度，指定-c 当前分支 -j 线程数
repo start 分支名	新建一个分支。
repo start 分支名 项目名  将分支分配给特定项目
repo status	显示当前分支的状态。
repo upload	将更改上传到审核服务器。
repo branches 查看现有分支的列表
repo diff 查看未提交的修改
git add	暂存文件。
git commit	提交暂存的文件。
git branch	显示当前分支。
git branch [branch]	创建新的主题分支。
git checkout [branch]	将 HEAD 切换到指定分支。
git merge [branch]	将 [branch] 合并到当前分支。
git diff	显示未暂存更改的 diff 结果。
git diff --cached	显示已暂存更改的 diff 结果。
git log	显示当前分支的历史记录。
git log m/[codeline]..	显示未推送的提交。
make clobber 删除整个out目录（rm -rf out/）
make clean 删除out/target/product/[product_name]（rm -rf $OUT）
```

### 构建

#### 构建系统

**Make** 构建系统得到了广泛的支持和使用，但在 Android 层面变得缓慢、容易出错、无法扩展且难以测试。因此，平台开发者应尽快从 Make 切换到 Soong。（.mk）

[Soong 构建系统](https://android.googlesource.com/platform/build/soong/+/refs/heads/master/README.md)是在 Android 7.0 (Nougat) 中引入的，旨在取代 Make。它利用 [Kati](https://github.com/google/kati/blob/master/README.md) GNU Make 克隆工具和 [Ninja](https://ninja-build.org/) 构建系统组件来加速 Android 的构建。(Blueprint 或 `.bp`)

##### Android.bp文件

`Android.bp` 文件很简单。它们不包含任何条件语句，也不包含控制流语句；所有复杂问题都由用 Go 编写的构建逻辑处理。

###### Modules

`Android.bp` 文件中的模块以[模块类型](https://ci.android.com/builds/latest/branches/aosp-build-tools/targets/linux/view/soong_build.html)开头，后跟一组 `name: "value",` 格式的属性：

Soong 模块参考：https://ci.android.com/builds/latest/branches/aosp-build-tools/targets/linux/view/soong_build.html

```shell
#每个模块都必须具有 name 属性，并且相应值在所有 name 文件中必须是唯一的，仅有两个例外情况是命名空间和预构建模块中的 Android.bp 属性值，这两个值可能会重复。srcs 属性以字符串列表的形式指定用于构建模块的源文件。您可以使用模块引用语法 ":<module-name>" 来引用生成源文件的其他模块的输出，如 genrule 或 filegroup。如需有效模块类型及其属性的列表，请参阅Soong 模块

cc_binary {
    name: "gzip",
    srcs: ["src/test/minigzip.c"],
    shared_libs: ["libz"],
    stl: "none",
}
```

###### Types

变量和属性是强类型，变量根据第一项赋值动态变化，属性由模块类型静态设置。支持的类型为：

- 布尔值（`true` 或 `false`）
- 整数 (`int`)
- 字符串 (`"string"`)
- 字符串列表 (`["string1", "string2"]`)
- 映射 (`{key1: "value1", key2: ["value2"]}`)

映射可以包含任何类型的值，包括嵌套映射。列表和映射可能在最后一个值后面有终止逗号。

###### Glob

接受文件列表的属性（例如 `srcs`）也可以采用 glob 模式。glob 模式可以包含普通的 UNIX 通配符 `*`，例如 `*.java`。glob 模式还可以包含单个 `**` 通配符作为路径元素，与零个或多个路径元素匹配。例如，`java/**/*.java` 同时匹配 `java/Main.java` 和 `java/com/android/Main.java` 模式。

###### Variables(变量)

`Android.bp` 文件可能包含顶级变量赋值，变量的作用域限定在声明它们的文件的其余部分，以及所有子 Blueprint 文件。变量是不可变的，但有一个例外情况：可以使用 `+=` 赋值将变量附加到别处，但只能在引用它们之前附加。

```shell
gzip_srcs = ["src/test/minigzip.c"],
cc_binary {
    name: "gzip",
    srcs: gzip_srcs,
    shared_libs: ["libz"],
    stl: "none",
}
```

###### Comments(注释)

`Android.bp` 文件可以包含 C 样式的多行 `/* */` 注释以及 C++ 样式的单行 `//` 注释。

###### Operators(运算符)

可以使用 + 运算符附加字符串、字符串列表和映射。可以使用 `+` 运算符对整数求和。附加映射会生成两个映射中键的并集，并附加在两个映射中都存在的所有键的值。

###### Conditionals(条件语句)

Soong 不支持 `Android.bp` 文件中的条件语句。但是，编译规则中需要条件语句的复杂问题将在 Go（在这种语言中，您可以使用高级语言功能，并且可以跟踪条件语句引入的隐式依赖项）中处理。大多数条件语句都会转换为映射属性，其中选择了映射中的某个值并将其附加到顶级属性。

例如，要支持特定于架构的文件，请使用以下命令：

```shell
cc_library {
    ...
    srcs: ["generic.cpp"],
    arch: {
        arm: {
            srcs: ["arm.cpp"],
        },
        x86: {
            srcs: ["x86.cpp"],
        },
    },
}
```

###### Formatter

Soong 包含一个针对 Blueprint 文件的规范格式设置工具，类似于 [gofmt](https://golang.org/cmd/gofmt/)。如需以递归方式重新设置当前目录中所有 `Android.bp` 文件的格式，请运行以下命令：

```shell
bpfmt -w .
```

规范格式包括缩进四个空格、多元素列表的每个元素后面有换行符，以及列表和映射末尾有英文逗号。

###### Special modules

* 默认模块

默认模块可用于在多个模块中重复使用相同的属性。例如：

```shell
cc_defaults {
    name: "gzip_defaults",
    shared_libs: ["libz"],
    stl: "none",
}

cc_binary {
    name: "gzip",
    defaults: ["gzip_defaults"],
    srcs: ["src/test/minigzip.c"],
}
```

* 预编译的模块

某些预构建的模块类型允许模块与其基于源代码的对应模块具有相同的名称。例如，如果已有同名的 `cc_binary`，也可以将 `cc_prebuilt_binary` 命名为 `foo`。这让开发者可以灵活地选择要纳入其最终产品中的版本。如果 build 配置包含两个版本，那么预编译模块定义中的 `prefer` 标志值会指示哪个版本具有优先级。请注意，某些预编译模块的名称不能以 `prebuilt`开头，例如 `android_app_import`。

* 命名空间模块

在 Android 完全从 Make 转换为 Soong 之前，Make 产品配置必须指定 `PRODUCT_SOONG_NAMESPACES` 值。它的值应该是一个以空格分隔的列表，其中包含 Soong 导出到 Make 以使用 `m` 命令进行编译的命名空间。在 Android 完成到 Soong 的转换之后，启用命名空间的详细信息可能会发生变化。

Soong 可以让不同目录中的模块指定相同的名称，只要每个模块都在单独的命名空间中声明即可。可以按如下方式声明命名空间：

```shell
soong_namespace {
    imports: ["path/to/otherNamespace1", "path/to/otherNamespace2"],
}
```

请注意，命名空间没有 name 属性；其路径会自动指定为其名称。

系统会根据每个 Soong 模块在树中的位置为其分配命名空间。每个 Soong 模块都会被视为处于 `Android.bp`（位于当前目录或最近的父级目录中的 `soong_namespace` 文件内）定义的命名空间中。如果未找到此类 `soong_namespace` 模块，则认为该模块位于隐式根命名空间中。

#### 构建Android

##### 设置环境

使用`envsetup.sh`脚本初始化环境：

脚本内容：https://android.googlesource.com/platform/build/+/refs/heads/master/envsetup.sh

```shell
source build/envsetup.sh

. build/envsetup.sh
```

需要在每次`repo sync`后重新执行此命令以获取对该脚本的任何更改

要查看可用命令的完整列表，请运行：

```shell
hmm
```

##### 构建目标

lunch 目标名(初始化环境会打印)

选择用`lunch`构建哪个目标。 `lunch product_name - build_variant`选择product_name作为要构建的产品，选择build_variant作为要构建的变体，并将这些选择存储在环境中，以供后续调用`m`和其他类似命令读取。

如果不带参数运行， `lunch`会提示您从菜单中选择一个目标，所有构建目标都采用`BUILD-BUILDTYPE`形式，其中`BUILD`是指代特定功能组合的代号。 `BUILDTYPE`是以下之一。

| Buildtype | Use                                                          |
| :-------- | :----------------------------------------------------------- |
| user      | Limited access; suited for production                        |
| userdebug | Like user but with root access and debug capability; preferred for debugging |
| eng       | Development configuration with additional debugging tools    |

`tapas`命令配置未捆绑应用程序的构建。它选择由 Android 构建系统构建的单个应用程序。与`lunch`不同， `tapas`不要求为设备构建图像。运行`tapas help`以获取有关该命令的更多信息。

##### 构建代码



