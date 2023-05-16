call runcrud.bat

if "%ERRORLEVEL%" == "0" goto openPage

goto fail

:openPage
start http://localhost:8080/crud/v1/task/tasks

:fail
echo.
echo RunCrud error

:end
echo.
echo Work is finished.