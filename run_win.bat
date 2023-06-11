@echo off

set src_dir=src
set out_dir=out
set lib_dir=lib
set doc_dir=docs

echo Compiling Java files...

set javac_path=
where /r "C:\Program Files";"C:\Program Files (x86)" javac.exe >nul 2>&1 && set javac_path=%errorlevel%
if not defined javac_path (
    echo javac not found. Searching the entire file system. This may take a while depending on the file system size.
    for /f "delims=" %%i in ('where /r \ javac.exe') do (
        set javac_path=%%i
        goto :found_javac
    )
)
:found_javac

if defined javac_path (
    "%javac_path%" -sourcepath "%src_dir%" -d "%out_dir%" -cp "%lib_dir%\*" -implicit:class "%src_dir%\BudgetManagementApp.java"
) else (
    echo ERROR: javac not found.
    exit /b 1
)

if %errorlevel% equ 0 (
    echo Compilation succeeded.
) else (
    echo Compilation failed.
    exit /b 1
)

echo Copying resources...
xcopy /s /y "%src_dir%\resources\*" "%out_dir%\resources\"

echo Generating documentation...

set javadoc_path=
where /r "C:\Program Files";"C:\Program Files (x86)" javadoc.exe >nul 2>&1 && set javadoc_path=%errorlevel%
if not defined javadoc_path (
    echo javadoc not found. Searching the entire file system. This may take a while depending on the file system size.
    for /f "delims=" %%i in ('where /r \ javadoc.exe') do (
        set javadoc_path=%%i
        goto :found_javadoc
    )
)
:found_javadoc

if defined javadoc_path (
    "%javadoc_path%" -d "%doc_dir%" -cp "%out_dir%;%lib_dir%\*" -subpackages controller:model:view -sourcepath "%src_dir%" -private -quiet -Xdoclint:none -author
) else (
    echo WARNING: javadoc not found.
)

echo Running Java application...

set java_path=
where /r "C:\Program Files";"C:\Program Files (x86)" java.exe >nul 2>&1 && set java_path=%errorlevel%
if not defined java_path (
    echo java not found. Searching the entire file system. This may take a while depending on the file system size.
    for /f "delims=" %%i in ('where /r \ java.exe') do (
        set java_path=%%i
        goto :found_java
    )
)
:found_java

if defined java_path (
    "%java_path%" -cp "%out_dir%;%lib_dir%\*" BudgetManagementApp
) else (
    echo ERROR: java not found.
    exit /b 1
)

if %errorlevel% equ 0 (
    echo Execution succeeded.
) else (
    echo Execution failed.
    exit /b 1
)
pause
