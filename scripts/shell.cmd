@echo off
setlocal

set AGENT_APPLICATION=..
set SPRING_PROFILES_ACTIVE=shell,severance

call .\support\agent.bat

endlocal