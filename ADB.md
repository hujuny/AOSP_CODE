## ADB

Android 调试桥 (`adb`) 是一种功能多样的命令行工具，可让您与设备进行通信。`adb` 命令可用于执行各种设备操作，例如安装和调试应用。`adb` 提供对 Unix shell（可用来在设备上运行各种命令）的访问权限。它是一种客户端-服务器程序，包括以下三个组件：

- **客户端**：用于发送命令。客户端在开发机器上运行。您可以通过发出 `adb` 命令从命令行终端调用客户端。
- **守护程序 (adbd)**：用于在设备上运行命令。守护程序在每个设备上作为后台进程运行。
- **服务器**：用于管理客户端与守护程序之间的通信。服务器在开发机器上作为后台进程运行。

`adb` 包含在 Android SDK 平台工具软件包中。您可以使用 [SDK 管理器](https://developer.android.google.cn/studio/intro/update?hl=zh-cn#sdk-manager)下载此软件包，该管理器会将其安装在 `android_sdk/platform-tools/` 下。

### 工作原理

当您启动某个 `adb` 客户端时，该客户端会先检查是否有 `adb` 服务器进程已在运行。如果没有，它会启动服务器进程。服务器在启动后会与本地 TCP 端口 5037 绑定，并监听 `adb` 客户端发出的命令。

### 命令

```shell
adb devices -l
adb kill-server
adb kill-server
# 多设备区分
adb -s 设备序列号
adb install path_to_apk
#端口转发
adb forward tcp:6100 tcp:7100
adb pull remote local
adb push local remote
adb --help
adb [-d |-e | -s serial_number] shell shell_command
#屏幕截图
adb shell screencap /sdcard/screen.png
# 屏幕录制，screenrecord 实用程序能以您要求的任何支持的分辨率和比特率进行录制，同时保持设备显示屏的宽高比。默认情况下，该实用程序以本机显示分辨率和屏幕方向进行录制，时长不超过三分钟。
#screenrecord 实用程序的局限性：
#音频不与视频文件一起录制。
#无法在搭载 Wear OS 的设备上录制视频。
#某些设备可能无法以它们的本机显示分辨率进行录制。如果在录制屏幕时出现问题，请尝试使用较低的屏幕分辨率。
#不支持在录制时旋转屏幕。如果在录制期间屏幕发生了旋转，则部分屏幕内容在录制时将被切断。
screenrecord [options] filename
adb shell screenrecord /sdcard/demo.mp4
--help	显示命令语法和选项
--size widthxheight	设置视频大小：1280x720。默认值为设备的本机显示屏分辨率（如果支持）；如果不支持，则为 1280x720。为获得最佳效果，请使用设备的 Advanced Video Coding (AVC) 编码器支持的大小。
--bit-rate rate	设置视频的视频比特率（以 MB/秒为单位）。默认值为 4Mbps。您可以增加比特率以提升视频质量，但这样做会导致视频文件变大。下面的示例将录制比特率设为 6Mbps：screenrecord --bit-rate 6000000 /sdcard/demo.mp4
--time-limit time	设置最长录制时间（以秒为单位）。默认情况下，最大值为 180（3 分钟）。
--rotate	将输出旋转 90 度。此功能处于实验阶段。
--verbose	在命令行屏幕显示日志信息。如果您不设置此选项，则该实用程序在运行时不会显示任何信息。
```

#### Logcat



Logcat 是一个命令行工具，用于在设备抛出错误并发送从您的应用使用 `Log` 类写入的消息时，转储系统消息日志。

Android 日志记录系统是系统进程 `logd` 维护的一组结构化环形缓冲区。这组可用的缓冲区是固定的，并由系统定义。最相关的缓冲区为：

- `main`：存储大多数应用日志。
- `system`：存储源自 Android OS 的消息。
- `crash`：存储崩溃日志。每个日志条目都包含一个优先级、一个标识日志来源的标记以及实际的日志消息。

日志记录系统的主接口是共享库 `liblog` 及其头文件 `<android/log.h>`。所有语言特定的日志记录工具最终都会调用函数 `__android_log_write`。默认情况下，它会调用函数 `__android_log_logd_logger`，该函数使用套接字将日志条目发送到 `logd`。从 API 级别 30 开始，可通过调用 `__android_set_log_writer` 更改日志记录函数。

```shell
#获取系统信息
adb shell getprop
adb shell getprop ro.build.version.release 系统版本
adb shell cat /system/build.prop
```



```shell
[adb] logcat [<option>] ... [<filter-spec>] ...
adb logcat --help
-b <buffer>	加载可供查看的备用日志缓冲区，例如 events 或 radio。默认使用 main、system 和 crash 缓冲区集。请参阅有关查看备用日志缓冲区的部分。
-c, --clear	清空所选的缓冲区并退出。默认缓冲区集为 main、system 和 crash。如需清空所有缓冲区，请使用 -b all -c。
-e <expr>, --regex=<expr>	只输出日志消息与 <expr> 匹配的行，其中 <expr> 是正则表达式。
-m <count>, --max-count=<count>	输出 <count> 行后退出。这样是为了与 --regex 配对，但可以独立运行。
--print	与 --regex 和 --max-count 配对，使内容绕过正则表达式过滤器，但仍能够在获得适当数量的匹配时停止。
-d	将日志转储到屏幕并退出。
-f <filename>	将日志消息输出写入 <filename>。默认值为 stdout。
-g, --buffer-size	输出指定日志缓冲区的大小并退出。
-n <count>	将轮替日志的数量上限设置为 <count>。默认值为 4。需要使用 -r 选项。
-r <kbytes>	每输出 <kbytes> 时轮替日志文件。默认值为 16。需要使用 -f 选项。
-s	相当于过滤器表达式 '*:S'；它将所有标记的优先级设为“静默”，并用于放在可添加内容的过滤器表达式列表之前。如需了解详情，请前往有关过滤日志输出的部分。
-v <format>	设置日志消息的输出格式。默认格式为 threadtime。如需查看支持的格式列表，请参阅有关控制日志输出格式的部分。
-D, --dividers	输出各个日志缓冲区之间的分隔线。
-c	清除整个日志并退出。
-t <count>	仅输出最新的行数。此选项包括 -d 功能。
-t '<time>'	输出自指定时间以来的最新行。此选项包括 -d 功能。如需了解如何引用带有嵌入空格的参数，请参阅 -P 选项。adb logcat -t '01-26 20:52:41.820'
-T <count>	输出自指定时间以来的最新行数。此选项不包括 -d 功能。
-T '<time>'	输出自指定时间以来的最新行。此选项不包括 -d 功能。如需了解如何引用带有嵌入空格的参数，请参阅 -P 选项。adb logcat -t '01-26 20:52:41.820'
-L, --last	在最后一次重新启动之前转储日志。
-B, --binary	以二进制文件形式输出日志。
-S, --statistics	在输出中包含统计信息，以帮助您识别和定位日志垃圾信息发送者。
-G <size>	设置日志环形缓冲区的大小。您可以在结尾处添加 K 或 M，以指示单位为千字节或兆字节。
-p, --prune	输出许可名单和拒绝名单，不采用任何参数，如下所示：adb logcat -p
-P '<list> ...'
--prune '<list> ...' -P '<allowlist_and_denylist>'	写入许可名单和拒绝名单，以出于特定目的调整日志记录内容。您可以提供允许和拒绝的名单条目的混合内容，其中 <allowlist> 或 <denylist> 可以是 UID、UID/PID 或 PID。在 logcat 统计信息 (logcat -S) 的指导下，您可以考虑出于各种目的调整许可名单和许可名单，例如：
通过 UID 选择使特定日志记录内容具有最长保留期限。
阻止人 (UID) 或物 (PID) 消耗相应资源，以帮助增加日志跨度，从而更深入地了解正在诊断的问题。
默认情况下，日志记录系统会自动以动态方式阻止日志统计信息中最严重的违规内容，以便为新的日志消息腾出空间。一旦它用尽启发法，系统便会删除最旧的条目，以便为新消息腾出空间。

添加许可名单可保护您的 Android 识别码 (AID)，它会变成进程的 AID 和 GID，而不会被声明为违规内容。添加拒绝名单有助于在相应内容被视为最严重的违规内容之前即释放空间。您可以选择删除内容的程度和频率；也可以关闭删除功能，这样，系统便仅会移除各个日志缓冲区中最旧条目的内容。
引号
adb logcat 不会保留引号，因此指定许可名单和拒绝名单的语法如下所示：
$ adb logcat -P '"<allowlist_and_denylist>"'

or

adb shell
$ logcat -P '<allowlist_and_denylist>'
--pid=<pid> ...	仅输出来自给定 PID 的日志。
--uid=<uids> ...	仅展示来自逗号分隔列表 <uids> 中显示的 UID 的日志消息。系统不会执行名称查询，因此 UID 必须以数值形式提供。此选项仅对“root”“log”和“system”用户有用，因为只有这些用户可以查看其他用户的日志。
--wrap	休眠 2 小时或者当缓冲区即将封装时（两者取其先）。通过提供即将封装唤醒来提高轮询的效率。
```

该表达式会抑制除标记为“ActivityManager”、优先级不低于“信息”的日志消息，以及标记为“MyApp”、优先级不低于“调试”的日志消息以外的所有其他日志消息

```shell
adb logcat ActivityManager:I MyApp:D *:S
```

上述表达式中最后一个元素 `*:S` 将所有标记的优先级设为“静默”，从而确保系统仅显示标记为“ActivityManager”和“MyApp”的日志消息。使用 `*:S` 可确保日志输出受限于您已明确指定的过滤器。`*:S` 可以让过滤器充当日志输出的“许可名单”。

优先级不低于“警告”的所有标记的所有日志消息

```shell
adb logcat *:W
```

##### 控制日志输出格式

除标记和优先级外，日志消息还包含许多元数据字段。您可以修改消息的输出格式，以便它们显示特定的元数据字段。为此，请使用 `-v` 选项，并指定下列某一受支持的输出格式：

- `brief`：显示优先级、标记以及发出消息的进程的 PID。
- `long`：显示所有元数据字段，并使用空白行分隔消息。
- `process`：仅显示 PID。
- `raw`：显示不包含其他元数据字段的原始日志消息。
- `tag`：仅显示优先级和标记。
- `thread:`：旧版格式，显示优先级、PID 以及发出消息的线程的 TID。
- `threadtime`（默认值）：显示日期、调用时间、优先级、标记、PID 以及发出消息的线程的 TID。
- `time`：显示日期、调用时间、优先级、标记以及发出消息的进程的 PID。

启动 `logcat` 时，您可以使用 `-v` 选项指定所需的输出格式：

```shell
[adb] logcat [-v <format>]
```

##### 格式修饰符

格式修饰符会更改 `logcat` 输出。如要指定格式修饰符，请使用 `-v` 选项

```shell
adb logcat -b all -v color -d
```

- `color`：使用不同的颜色来显示每个优先级。
- `descriptive`：显示日志缓冲区事件说明。此修饰符仅影响事件日志缓冲区消息，不会对其他非二进制文件缓冲区产生任何影响。事件说明取自 event-log-tags 数据库。
- `epoch`：显示自 1970 年 1 月 1 日以来的时间（以秒为单位）。
- `monotonic`：显示自上次启动以来的时间（以 CPU 秒为单位）。
- `printable`：确保所有二进制日志记录内容都进行了转义。
- `uid`：如果访问权限控制允许，则显示 UID 或记录的进程的 Android ID。
- `usec`：显示精确到微秒的时间。
- `UTC`：显示世界协调时间 (UTC)。
- `year`：将年份添加到显示的时间。
- `zone`：将本地时区添加到显示的时间。

##### 备用日志缓冲区

Android 日志记录系统为日志消息保留了多个环形缓冲区，而且并非所有的日志消息都会发送到默认的环形缓冲区。如要查看其他日志消息，您可以使用 `-b` 选项运行 `logcat` 命令，以请求查看备用的环形缓冲区。您可以查看下列任意备用缓冲区：

- `radio`：查看包含无线装置/电话相关消息的缓冲区。
- `events`：查看已经过解译的二进制系统事件缓冲区消息。
- `main`：查看主日志缓冲区（默认），不包含系统和崩溃日志消息。
- `system`：查看系统日志缓冲区（默认）。
- `crash`：查看崩溃日志缓冲区（默认）。
- `all`：查看所有缓冲区。
- `default`：报告 `main`、`system` 和 `crash` 缓冲区。

```shell
[adb] logcat [-b <buffer>]
#为要输出的所有缓冲区指定多个 -b 标记
logcat -b main -b radio -b events
#指定一个 -b 标记，后跟缓冲区的逗号分隔列表
logcat -b main,radio,events
```

#### Activity管理器（am）

**发出命令以执行各种系统操作，如启动 activity、强行停止进程、广播 intent、修改设备屏幕属性，等等。**

##### start [options] intent

```shell
#am activity管理器
adb shell am start -a android.intent.action.VIEW
-D：启用调试功能。
-W：等待启动完成。
--start-profiler file：启动性能分析器并将结果发送至 file。
-P file：类似于 --start-profiler，但当应用进入空闲状态时剖析停止。
-R count：重复启动 activity count 次。在每次重复前，将完成顶层 activity。
-S：在启动 activity 前，强行停止目标应用。
--opengl-trace：启用 OpenGL 函数的跟踪。
--user user_id | current：指定要作为哪个用户运行；如果未指定，则作为当前用户运行。
```

##### startservice [options] intent

```shell
--user user_id | current：指定要作为哪个用户运行；如果未指定，则作为当前用户运行。
```

##### broadcast [options] intent

```shell
[--user user_id | all | current]：指定要发送给哪个用户；如果未指定，则发送给所有用户。
```

##### instrument [options] component

```shell
使用 Instrumentation 实例启动监控。通常情况下，目标 component 采用 test_package/runner_class 格式。
具体选项包括：

-r：输出原始结果（否则，对 report_key_streamresult 进行解码）。与 [-e perf true] 结合使用可生成性能测量的原始输出。
-e name value：将参数 name 设为 value。 对于测试运行程序，通用格式为 -e testrunner_flag value[,value...]。
-p file：将剖析数据写入 file。
-w：等待插桩完成后再返回。测试运行程序需要使用此选项。
--no-window-animation：运行时关闭窗口动画。
--user user_id | current：指定以哪个用户身份运行插桩。如果未指定，则以当前用户身份运行。
```

##### intent 参数的规范

```shell
-a action
指定 intent 操作，如 android.intent.action.VIEW。只能声明一次。
-d data_uri
指定 intent 数据 URI，如 content://contacts/people/1。只能声明一次。
-t mime_type
指定 intent MIME 类型，如 image/png。只能声明一次。
-c category
指定 intent 类别，如 android.intent.category.APP_CONTACTS。
-n component
指定带有软件包名称前缀的组件名称，以创建显式 intent，如 com.example.app/.ExampleActivity。
-f flags
向 setFlags() 支持的 intent 添加标志。
--esn extra_key
添加一个空 extra。URI intent 不支持此选项。
-e | --es extra_key extra_string_value
以键值对的形式添加字符串数据。
--ez extra_key extra_boolean_value
以键值对的形式添加布尔值数据。
--ei extra_key extra_int_value
以键值对的形式添加整数型数据。
--el extra_key extra_long_value
以键值对的形式添加长整型数据。
--ef extra_key extra_float_value
以键值对的形式添加浮点型数据。
--eu extra_key extra_uri_value
以键值对的形式添加 URI 数据。
--ecn extra_key extra_component_name_value
添加组件名称，该名称作为 ComponentName 对象进行转换和传递。
--eia extra_key extra_int_value[,extra_int_value...]
添加整数数组。
--ela extra_key extra_long_value[,extra_long_value...]
添加长整数数组。
--efa extra_key extra_float_value[,extra_float_value...]
添加浮点数数组。
--grant-read-uri-permission
添加 FLAG_GRANT_READ_URI_PERMISSION 标志。
--grant-write-uri-permission
添加 FLAG_GRANT_WRITE_URI_PERMISSION 标志。
--debug-log-resolution
添加 FLAG_DEBUG_LOG_RESOLUTION 标志。
--exclude-stopped-packages
添加 FLAG_EXCLUDE_STOPPED_PACKAGES 标志。
--include-stopped-packages
添加 FLAG_INCLUDE_STOPPED_PACKAGES 标志。
--activity-brought-to-front
添加 FLAG_ACTIVITY_BROUGHT_TO_FRONT 标志。
--activity-clear-top
添加 FLAG_ACTIVITY_CLEAR_TOP 标志。
--activity-clear-when-task-reset
添加 FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET 标志。
--activity-exclude-from-recents
添加 FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS 标志。
--activity-launched-from-history
添加 FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY 标志。
--activity-multiple-task
添加 FLAG_ACTIVITY_MULTIPLE_TASK 标志。
--activity-no-animation
添加 FLAG_ACTIVITY_NO_ANIMATION 标志。
--activity-no-history
添加 FLAG_ACTIVITY_NO_HISTORY 标志。
--activity-no-user-action
添加 FLAG_ACTIVITY_NO_USER_ACTION 标志。
--activity-previous-is-top
添加 FLAG_ACTIVITY_PREVIOUS_IS_TOP 标志。
--activity-reorder-to-front
添加 FLAG_ACTIVITY_REORDER_TO_FRONT 标志。
--activity-reset-task-if-needed
添加 FLAG_ACTIVITY_RESET_TASK_IF_NEEDED 标志。
--activity-single-top
添加 FLAG_ACTIVITY_SINGLE_TOP 标志。
--activity-clear-task
添加 FLAG_ACTIVITY_CLEAR_TASK 标志。
--activity-task-on-home
添加 FLAG_ACTIVITY_TASK_ON_HOME 标志。
--receiver-registered-only
添加 FLAG_RECEIVER_REGISTERED_ONLY 标志。
--receiver-replace-pending
添加 FLAG_RECEIVER_REPLACE_PENDING 标志。
--selector
需要使用 -d 和 -t 选项设置 intent 数据和类型。
URI component package
如果不受上述任一选项的限制，您可以直接指定 URI、软件包名称和组件名称。当某个参数不受限制时，如果该参数包含“:”（英文冒号），那么该工具会假定参数是 URI。如果该参数包含“/”（正斜线），那么该工具会假定参数是组件名称。如果并非上述两种情况，那么该工具会假定参数是软件包名称。
```

##### 其它

* 强行停止与 `package` 关联的所有进程。

```shell
force-stop package
```

*  终止与 `package` 关联的所有进程。此命令仅终止可安全终止且不会影响用户体验的进程。

```shell
kill [options] package
--user user_id | all | current：指定要终止哪个用户的进程。如果未指定，则终止所有用户的进程。
```

*  终止所有后台进程

```shell
kill-all
```

* 启动 `process` 的性能分析器，将结果写入 `file`。

```shell
profile start process file
```

* 停止 `process` 的性能分析器。

```shell
profile stop process
```

* 转储 `process` 的堆，写入 `file`。

```shell
dumpheap [options] process file
--user [user_id | current]：提供进程名称时，指定要转储的进程的用户。如果未指定，则使用当前用户。
-n：转储原生堆，而非托管堆。
```

* 设置要调试的应用 `package`。

```shell
set-debug-app [options] package
-w：应用启动时等待调试程序。
--persistent：保留此值。
```

* 清除之前使用 `set-debug-app` 设置的用于调试的软件包。

```shell
clear-debug-app
```

* 开始监控崩溃或 ANR。

```shell
monitor [options]
--gdb：在崩溃/ANR 时，在给定的端口上启动 gdbserv。
```

* 控制 `package` 的[屏幕兼容性](https://developer.android.google.cn/guide/practices/screen-compat-mode?hl=zh-cn)模式。

```shell
screen-compat {on | off} package
```

* 替换设备显示尺寸

```shell
display-size [reset | widthxheight]
am display-size 1280x800
```

* 替换设备显示密度

```shell
display-density dpi
am display-density 480	
```

* 以 URI 的形式输出给定的 intent 规范。

```shell
to-uri intent
```

* 以 `intent:` URI 的形式输出给定的 intent 规范。

```shell
to-intent-uri intent
```

#### 软件包管理器 (`pm`)

**对设备上安装的应用软件包执行操作和查询。**

```shell
adb shell pm uninstall com.example.MyApp
```

* 输出所有软件包，或者视需要仅输出软件包名称包含 `filter` 中文字的软件包。

```shell
list packages [options] filter
-f：查看关联文件。
-d：进行过滤以仅显示已停用的软件包。
-e：进行过滤以仅显示已启用的软件包。
-s：进行过滤以仅显示系统软件包。
-3：进行过滤以仅显示第三方软件包。
-i：查看软件包的安装程序。
-u：包括已卸载的软件包。
--user user_id：要查询的用户空间。
```

* 输出所有已知的权限组。

```shell
list permission-groups
```

* 输出所有已知的权限，或者视需要仅输出 `group` 中的权限。

```shell
list permissions [options] group
-g：按组进行整理。
-f：输出所有信息。
-s：简短摘要。
-d：仅列出危险权限。
-u：仅列出用户将看到的权限。
```

* 列出所有测试软件包。

```shell
list instrumentation [options]	
-f：列出测试软件包的 APK 文件。
target_package：仅列出此应用的测试软件包。
```

* 输出系统的所有功能。

```shell
list features
```

* 输出当前设备支持的所有库。

```shell
list libraries
```

* 输出系统中的所有用户。

```shell
list users
```

* 出给定 `package` 的 APK 的路径。

```shell
path package
```

* 将软件包（通过 `path` 指定）安装到系统。

```shell
install [options] path
-r：重新安装现有应用，并保留其数据。
-t：允许安装测试 APK。仅当您运行或调试了应用或者使用了 Android Studio 的 Build > Build APK 命令时，Gradle 才会生成测试 APK。如果是使用开发者预览版 SDK 构建的 APK，那么安装测试 APK 时必须在 install 命令中包含 -t 选项。
-i installer_package_name：指定安装程序软件包名称。
--install-location location：使用以下某个值设置安装位置：
0：使用默认安装位置。
1：在设备内部存储空间中安装。
2：在外部介质上安装。
-f：在内部系统内存上安装软件包。
-d：允许版本代码降级。
-g：授予应用清单中列出的所有权限。
--fastdeploy：通过仅更新已更改的 APK 部分来快速更新安装的软件包。
--incremental：仅安装 APK 中启动应用所需的部分，同时在后台流式传输剩余数据。如要使用此功能，您必须为 APK 签名，创建一个 APK 签名方案 v4 文件，并将此文件放在 APK 所在的目录中。只有部分设备支持此功能。此选项会强制 adb 使用该功能，如果该功能不受支持，则会失败，并提供有关失败原因的详细信息。附加 --wait 选项，可等到 APK 完全安装完毕后再授予对 APK 的访问权限。
--no-incremental 可阻止 adb 使用此功能。
```

* 从系统中移除软件包。

```shell
uninstall [options] package
-k：移除软件包后保留数据和缓存目录。
```

*  删除与软件包关联的所有数据。

```shell
clear package
```

* 启用给定的软件包或组件（写为“package/class”）。

```shell
enable package_or_component
```

* 停用给定的软件包或组件（写为“package/class”）。

```shell
disable package_or_component
disable-user [options] package_or_component
--user user_id：要停用的用户。
```

* 向应用授予权限。在搭载 Android 6.0（API 级别 23）及更高版本的设备上，该权限可以是应用清单中声明的任何权限。在搭载 Android 5.1（API 级别 22）及更低版本的设备上，该权限必须是应用定义的可选权限。

```shell
grant package_name permission
```

*  从应用撤消权限。在搭载 Android 6.0（API 级别 23）及更高版本的设备上，该权限可以是应用清单中声明的任何权限。在搭载 Android 5.1（API 级别 22）及更低版本的设备上，该权限必须是应用定义的可选权限。

```shell
revoke package_name permission
```

* 更改默认安装位置。

```shell
set-install-location location
0：自动 - 让系统决定最合适的位置。
1：内部 - 在设备内部存储空间中安装。
2：外部 - 在外部介质上安装。
```

* 返回当前安装位置

```shell
get-install-location
```

* 指定是否应强制执行指定权限。

```shell
set-permission-enforced permission [true | false]
```

* 减少缓存文件以达到给定的可用空间。

```shell
trim-caches desired_free_space
```

* 创建具有给定 `user_name` 的新用户，从而输出该用户的新用户标识符

```shell
create-user user_name
```

* 移除具有给定 `user_id` 的用户，从而删除与该用户关联的所有数据。

```shell
remove-user user_id
```

* 输出设备支持的最大用户数。

```shell
get-max-users
```

* 输出给定 package 的域名验证状态，如果未指定软件包，则输出所有软件包的域名验证状态。

  状态代码的定义如下：

  - `none`：没有为此域名记录任何内容
  - `verified`：域名已成功通过验证
  - `approved`：强行批准了域名，通常是通过执行 shell 命令来实现的
  - `denied`：强行拒绝了域名，通常是通过执行 shell 命令来实现的
  - `migrated`：从旧响应流程中保留的验证状态
  - `restored`：从用户数据恢复流程中保留的验证状态
  - `legacy_failure`：旧版验证程序拒绝了域名，原因未知
  - `system_configured`：设备配置自动批准了域名
  - `>= 1024`：设备验证程序专属的自定义错误代码

```shell
get-app-links [options] [package]
--user user_id：包括用户选择的域名。涵盖所有域名，而不仅仅是执行 autoVerify 的域名。
```

* 重置给定软件包的域名验证状态，如果未指定任何软件包，则重置所有软件包的域名验证状态。

```shell
reset-app-links [options] [package]
package：要重置的软件包，如果使用“all”，则重置所有软件包
--user user_id：包括用户选择的域名。涵盖所有域名，而不仅仅是执行 autoVerify 的域名。
```

* 广播给定 package 的域名验证请求，如果未指定软件包，则发送所有软件包的域名验证请求。仅当软件包之前未记录响应时发送该请求。

```shell
verify-app-links [--re-verify] [package]
--re-verify：即使软件包已记录响应也发送
```

* 手动设置软件包的域名状态。仅当软件包将域名声明为 autoVerify 时，此命令才能正常运行。此命令不会针对无法应用的域名报告失败。

```shell
set-app-links [--package package] state domains
--package package：要设置的软件包，如果使用“all”，则设置所有软件包
state：要为域名设置的代码。有效值为：
STATE_NO_RESPONSE (0)：按未记录过任何响应的情况进行重置。
STATE_SUCCESS (1)：将域名视为已成功通过域名验证代理的验证。请注意，域名验证代理可以覆盖此设置。
STATE_APPROVED (2)：将域名视为一律批准，防止域名验证代理更改状态。
STATE_DENIED (3)：将域名视为一律拒绝，防止域名验证代理更改状态。
domains：要更改的域名的列表（以空格分隔），如果使用“all”，则更改所有域名。
```

* 手动设置主机用户针对软件包选择的域名的状态。仅当软件包声明相应域名时，此命令才能正常运行。此命令不会针对无法应用的域名报告失败。

```shell
set-app-links-user-selection --user user_id [--package package] enabled domains
--user user_id：要更改哪位用户所做的选择
--package package：要设置的软件包
enabled：是否批准域名
domains：要更改的域名的列表（以空格分隔），如果使用“all”，则更改所有域名
```

* 手动设置主机用户针对软件包选择的域名的状态。仅当软件包声明相应域名时，此命令才能正常运行。此命令不会针对无法应用的域名报告失败。

```shell
set-app-links-user-selection --user user_id [--package package] enabled domains
--user user_id：要更改哪位用户所做的选择
--package package：要设置的软件包
enabled：是否批准域名
domains：要更改的域名的列表（以空格分隔），如果使用“all”，则更改所有域名
```

* 切换软件包的自动验证链接处理设置。

```shell
set-app-links-allowed --user user_id [--package package] allowed
--user user_id：要更改哪位用户所做的选择
--package package：要设置的软件包，如果使用“all”，则设置所有软件包；如果未指定任何软件包，则重置软件包
allowed：值为 true 时，表示允许软件包打开自动验证链接；值为 false 时，表示不允许这么做
```

* 为给定用户输出特定域名的所有者（按优先级从低到高的顺序排列）。

```shell
get-app-link-owners --user user_id [--package package] domains
--user user_id：要查询的用户
--package package：（可选）同时针对软件包声明的所有域名输出结果；如果使用“all”，则针对所有软件包声明的所有域名输出结果
domains：要查询的域名的列表（以空格分隔）
```

#### dumpsys

`dumpsys` 是一种在 Android 设备上运行的工具，可提供有关系统服务的信息。可以使用 [Android 调试桥 (adb)](https://developer.android.google.cn/studio/command-line/adb?hl=zh-cn) 从命令行调用 `dumpsys`，获取在连接的设备上运行的所有系统服务的诊断输出。

此输出通常比您想要的更详细，因此请使用此页面上的[命令行选项](https://developer.android.google.cn/studio/command-line/dumpsys?hl=zh-cn#options)仅获取您所需的系统服务的输出。

检查输入、RAM、电池或网络诊断信息。

```shell
 adb shell dumpsys [-t timeout] [--help | -l | --skip services | service [arguments] | -c | -h]
 -t timeout	指定超时期限（秒）。如果未指定，默认值为 10 秒。
--help	输出 dumpsys 工具的帮助文本。
-l	输出可与 dumpsys 配合使用的系统服务的完整列表。
--skip services	指定您不希望包含在输出中的 services。
service [arguments]	指定您希望输出的 service。某些服务可能允许您传递可选 arguments。如需了解这些可选参数，请将 -h 选项与服务一起传递：adb shell dumpsys procstats -h
-c	指定某些服务时，附加此选项能以计算机可读的格式输出数据。
-h	对于某些服务，附加此选项可查看该服务的帮助文本和其他选项。
#查找应用的 UID
adb shell dumpsys package your-package-name
```

* 查看可与 `dumpsys` 配合使用的系统服务的完整列表

```shell
adb shell dumpsys -l
```

##### 输入诊断（Input）

* 指定 `input` 服务（如以下命令所示）可转储系统输入设备（如键盘和触摸屏）的状态以及输入事件的处理。

  * Event Hub 状态

  * Input Reader 状态

    `InputReader` 负责对来自内核的输入事件进行解码。其状态转储会显示各输入设备的配置信息以及最近发生的状态变化，如按下按键或轻触触摸屏等操作。在 Input Reader 状态转储的结尾部分，会显示一些关于全局配置参数的信息，例如点按时间间隔：

  * Input Dispatcher 状态

    `InputDispatcher` 负责向应用发送输入事件。如以下示例输出所示，其状态转储显示许多方面的信息，包括正在轻触哪个窗口、输入队列的状态、是否正在进行 ANR 以及其他输入事件信息：

```shell
adb shell dumpsys input
```

###### **检查事项**

​	**Event Hub 状态：**

- 所有预期的输入设备是否都存在。

- 每个输入设备是否都有适当的按键布局文件、按键字符映射文件和输入设备配置文件。如果这些文件缺失或包含语法错误，则无法加载它们。

- 每个输入设备是否都已正确分类。`Classes` 字段中的位是否对应于 `EventHub.h` 中的标志，如 `INPUT_DEVICE_CLASS_TOUCH_MT`。

- `BuiltInKeyboardId` 是否正确无误。如果设备未配备内置键盘，则该 ID 必须为 `-2`。否则，应为内置键盘的 ID。

- - 如果您发现 `BuiltInKeyboardId` 应该为 `-2`，但却不是，则说明缺少某个特殊功能小键盘的按键字符映射文件。特殊功能小键盘设备应具有仅包含 `type SPECIAL_FUNCTION` 行的按键字符映射文件。

**Input Reader 状态：**

- 所有的预期输入设备是否都存在。
- 每个输入设备是否都已配置正确。特别注意检查触摸屏和操纵杆轴是否正确。

**Input Dispatcher 状态：**

- 所有输入事件是否均按预期进行处理。
- 轻触触摸屏的同时运行 `dumpsys` 后，`TouchStates` 行是否正确标识了您所轻触的窗口。

##### 测试界面性能（gfxinfo ）

如果指定 `gfxinfo` 服务，输出中会包含录制阶段所发生的动画帧的相关性能信息。

收集指定软件包名称的界面性能数据

```shell
adb shell dumpsys gfxinfo package-name
```

包含 `framestats` 选项，以提供有关最近发生的帧的更加详细的帧时间信息，让您能够更准确地找到问题并进行调试：

```shell
adb shell dumpsys gfxinfo package-name framestats
```

##### 网络诊断信息

指定 `netstats` 服务可提供自设备上次启动以来收集的网络使用情况统计信息。若要输出额外信息，如详细的唯一用户 ID (UID) 信息，请包含 `detail` 选项

```shell
adb shell dumpsys netstats detail
```

信息类型

活动接口和活动 UID 接口；“Dev”和“Xt”统计信息

* 指定应用的网络使用情况

```shell
adb shell dumpsys package com.example.myapp | grep userId
```

##### 电池诊断信息（batterystats ）

指定 `batterystats` 服务会生成关于设备电池用量的统计数据，这些数据按唯一用户 ID (UID) 进行整理。

```shell
adb shell dumpsys batterystats options
```

查看 `batterystats` 可用的其他选项列表,请添加 `-h` 选项。

输出自设备上次充电以来指定应用软件包的电池用量统计信息

```shell
adb shell dumpsys batterystats --charged package-name
```

输出内容

- 电池相关事件的历史记录
- 设备的全局统计信息
- 每个 UID 和系统组件的大致用电量
- 单个应用的每个数据包占用的移动网络毫秒数
- 系统 UID 汇总统计信息
- 应用 UID 汇总统计信息

以计算机可读的 CSV 格式生成 `batterystats` 输出

```shell
adb shell dumpsys batterystats --checkin
```

##### 内存分配情况(procstats/meminfo)

使用 `procstats` 检查一段时间内的占用情况，或使用 `meminfo` 检查特定时间点的占用情况。



#### 设备政策管理器 (`dpm`)

* 将 component 设为活动管理。

```shell
set-active-admin [options] component
--user user_id：指定目标用户。您也可以传递 --user current 以选择当前用户。
```

* 将 component 设为活跃管理员，并将其软件包设为现有用户的个人资料所有者。

```shell
set-profile-owner [options] component
--user user_id：指定目标用户。您也可以传递 --user current 以选择当前用户。
--name name：指定简单易懂的组织名称。
```

* 将 component 设为活跃管理员，并将其软件包设为设备所有者。

```shell
set-device-owner [options] component
--user user_id：指定目标用户。您也可以传递 --user current 以选择当前用户。
--name name：指定简单易懂的组织名称。
```

* 停用活跃管理员。应用必须在清单中声明 `android:testOnly`。此命令还会移除设备所有者和个人资料所有者。

```shell
remove-active-admin [options] component
--user user_id：指定目标用户。您也可以传递 --user current 以选择当前用户。
```

* 清除设备之前设置的系统 OTA 更新冻结期记录。在开发管理冻结期的应用时，这有助于避免设备存在调度方面的限制。

  在搭载 Android 9.0（API 级别 28）及更高版本的设备上受支持。

```shell
clear-freeze-period-record
```

* 强制系统让任何现有网络日志随时可供 DPC 检索。如果有可用的连接或 DNS 日志，DPC 会收到 `onNetworkLogsAvailable()` 回调。此命令有调用频率限制。在搭载 Android 9.0（API 级别 28）及更高版本的设备上受支持。

```shell
force-network-logs
```

*  强制系统向 DPC 提供任何现有安全日志。如果有可用的日志，DPC 会收到 `onSecurityLogsAvailable()` 回调。此命令有调用频率限制。在搭载 Android 9.0（API 级别 28）及更高版本的设备上受支持。

```shell
force-security-logs
```

#### 读取应用的 ART 配置文件

从 Android 7.0（API 级别 24）开始，Android 运行时 (ART) 会收集已安装应用的执行配置文件，这些配置文件用于优化应用性能。您可以检查收集的配置文件，了解在应用启动期间，系统频繁执行了哪些方法和使用了哪些类。

必须拥有对文件系统的 root 权限（例如在模拟器上），才能检索执行配置文件的文件名。

* 生成文本格式的配置文件信息

```shell
adb shell cmd package dump-profiles package
adb pull /data/misc/profman/package.prof.txt
```

#### 重置测试设备

如果您在多个测试设备上测试应用，则在两次测试之间重置设备可能很有用，例如，可以移除用户数据并重置测试环境。您可以使用 `testharness` `adb` shell 命令对搭载 Android 10（API 级别 29）或更高版本的测试设备执行恢复出厂设置，如下所示。

```shell
adb shell cmd testharness enable
```

使用 `testharness` 恢复设备时，设备会自动将允许通过当前工作站调试设备的 RSA 密钥备份在一个持久性位置。也就是说，在重置设备后，工作站可以继续调试设备并向设备发出 `adb` 命令，而无需手动注册新密钥。

使用 `testharness` 恢复设备还会更改以下设备设置：

- 设备会设置某些系统设置，以便不会出现初始设备设置向导。也就是说，设备会进入一种状态，供您快速安装、调试和测试您的应用。
- 设置：
  - 停用锁定屏幕。
  - 停用紧急警报。
  - 停用帐号自动同步。
  - 停用自动系统更新。
- 其他：
  - 停用预安装的安全应用。

如果您的应用需要检测并适应 `testharness` 命令的默认设置，请使用 [`ActivityManager.isRunningInUserTestHarness()`](https://developer.android.google.cn/reference/android/app/ActivityManager?hl=zh-cn#isRunningInUserTestHarness())。

#### sqlite

`sqlite3` 可启动用于检查 SQLite 数据库的 `sqlite` 命令行程序。它包含用于输出表格内容的 `.dump` 以及用于输出现有表格的 `SQL CREATE` 语句的 `.schema` 等命令。

更多命令

https://www.sqlite.org/cli.html

```
 sqlite3 /data/data/com.example.app/databases/rssitems.db
```