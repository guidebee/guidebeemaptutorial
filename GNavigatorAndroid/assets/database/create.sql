CREATE TABLE `Device` (`_id` integer primary key autoincrement,`deviceNo` text,`deviceImei` text,`deviceName` text,`deviceStatus` text,`latitude` text,`longitude` text,`devicePowerLevel` text);
CREATE TABLE `DeviceLocationHistory` (`_id` integer primary key autoincrement,`deviceNo` text,`timeStamp` long,`latitude` text,`longitude` text);
CREATE TABLE `DeviceGeofence` (`_id` integer primary key autoincrement,`deviceNo` text,`latitude` text,`longitude` text);
CREATE TABLE `DeviceCommandHistory` (`_id` integer primary key autoincrement,`deviceNo` text,`command` text,`response` text,`timeStamp` long);
CREATE TABLE `Favoriate` (`_id` integer primary key autoincrement,`addressName` text,`addressSymbolIndex` long,`latitude` text,`longitude` text);
