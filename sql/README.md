### SQL QUERIES


###### Table Setup (DDL)
REATE TABLE cd.members (
  memid INTEGER NOT NULL, 
  surname character VARCHAR (200) NOT NULL, 
  firstname character VARCHAR(200) NOT NULL, 
  address character VARCHAR(300) NOT NULL, 
  zipcode INTEGER NOT NULL, 
  telephone character VARCHAR(20) NOT NULL, 
  recommendedby INTEGER, 
  joindate TIMESTAMP NOT NULL, 
  CONSTRAINT members_pk PRIMARY KEY (memid), 
  CONSTRAINT fk_members_recommendedby FOREIGN KEY(recommendedby) REFERENCES cd.members(memid) ON DELETE SET NULL
);
CREATE TABLE cd.facilities (
  facid INTEGER NOT NULL, 
  name character VARCHAR(100) NOT NULL, 
  membercost NUMERIC NOT NULL, 
  guestcost NUMERIC NOT NULL, 
  initialoutlay NUMERIC NOT NULL, 
  monthlymaintenance NUMERIC NOT NULL, 
  CONSTRAINT facilities_pk PRIMARY KEY (facid)
);
CREATE TABLE cd.bookings (
  bookid integer NOT NULL, 
  facid integer NOT NULL, 
  memid integer NOT NULL, 
  starttime timestamp NOT NULL, 
  slots integer NOT NULL, 
  CONSTRAINT bookings_pk PRIMARY KEY (bookid), 
  CONSTRAINT fk_bookings_facid FOREIGN KEY (facid) REFERENCES cd.facilities(facid), 
  CONSTRAINT fk_bookings_memid FOREIGN KEY (memid) REFERENCES cd.members(memid)
);



