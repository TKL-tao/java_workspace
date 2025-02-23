@echo off
setlocal

:: 检查Chrome是否安装在默认路径
set "chrome_path=%ProgramFiles%\Google\Chrome\Application\chrome.exe"
if not exist "%chrome_path%" set "chrome_path=%ProgramFiles(x86)%\Google\Chrome\Application\chrome.exe"
if not exist "%chrome_path%" (
    echo 错误：未找到Chrome，请确保已安装。
    pause
    exit /b
)

:: 获取脚本所在目录的绝对路径
set "base_dir=%~dp0"

:: 构建三个文件的完整路径
set "files="
call :add_file "LinuxLearning\LinuxLearning.html"
call :add_file "JavaLearning\JavaLearning.html"
call :add_file "SQLLearning\SQLLearning.html"
call :add_file "SpringLearning\SpringLearning.html"

:: 启动Chrome并打开所有文件
start "" "%chrome_path%" %files%

endlocal
exit /b

:add_file
set "file_path=%base_dir%%~1"
if not exist "%file_path%" (
    echo 警告：文件未找到 - %file_path%
) else (
    set "files=%files% "%file_path%""
)
exit /b
