ECHO "Script Running"  
START /w java -jar selenium-server-standalone-2.47.1.jar -role node -hub http://10.4.1.220:4444/grid/register -Dwebdriver.ie.driver=.\IEDriverServer.exe -Dwebdriver.chrome.driver=.\chromedriver.exe

