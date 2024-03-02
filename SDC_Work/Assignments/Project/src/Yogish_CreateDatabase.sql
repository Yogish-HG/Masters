CREATE TABLE `Publications` (
  `identifier` VARCHAR(45) NOT NULL PRIMARY KEY,
  `title` VARCHAR(45) NOT NULL,
  `journal` VARCHAR(45) NULL DEFAULT NULL REFERENCES `VenueInformation`(`venuename`) ON UPDATE CASCADE,
  `pages` VARCHAR(45) NOT NULL,
  `volume` VARCHAR(45)NULL,
  `issue` VARCHAR(45) NULL,
  `pmonth` VARCHAR(45) NULL,
  `pyear` VARCHAR(45) NOT NULL,
  `conference` VARCHAR(45) NULL REFERENCES `VenueInformation`(`venuename`) ON UPDATE CASCADE,
  `location` VARCHAR(45) NULL);
  
  CREATE TABLE `AuthorIdentifier` (
  `identifier` VARCHAR(45) NOT NULL REFERENCES `Publications`(`identifier`) ON UPDATE CASCADE,
  `author` VARCHAR(45) NOT NULL);
  
   CREATE TABLE `Referenceidentifier` (
  `identifier` VARCHAR(45) NOT NULL REFERENCES `Publications`(`identifier`) ON UPDATE CASCADE,
  `paperreferences` VARCHAR(45) NOT NULL );
  
    CREATE TABLE `AreaHierarchy` (
  `area` VARCHAR(45) NOT NULL PRIMARY KEY,
  `parentarea` VARCHAR(45) NULL);
  
    CREATE TABLE `VenueInformation` (
  `venuename` VARCHAR(45) NOT NULL PRIMARY KEY,
  `publisher` VARCHAR(45) NOT NULL REFERENCES `Publisher`(`contactname`) ON UPDATE CASCADE,
  `editor` VARCHAR(45) NOT NULL,
  `editorcontact` VARCHAR(45) NULL,
  `location` VARCHAR(45) NULL,
  `conferenceyear` VARCHAR(45) NULL);
  
  CREATE TABLE `VenueResearchAreas` (
  `venuename` VARCHAR(45) NOT NULL REFERENCES `AreaHierarchy`(`area`) ON UPDATE CASCADE,
  `area` VARCHAR(45) NOT NULL REFERENCES `VenueInformation`(`venuename`) ON UPDATE CASCADE);
  
    CREATE TABLE `Publisher` (
  `publisheridentifier` VARCHAR(45) NOT NULL PRIMARY KEY,
  `contactname` VARCHAR(45) NOT NULL UNIQUE KEY,
  `contactemail` VARCHAR(45) NULL,
  `location` VARCHAR(45) NOT NULL);
