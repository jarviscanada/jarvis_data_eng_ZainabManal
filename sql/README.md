# Introduction
"Below,are a set of SQL queries for practicing database querying. Three tables have been created: a members table, a facilities table, and a bookings table. These queries cover a range of topics including modifying data, basic database querying, joins, aggregates, and string querying. "  

# SQL QUERIES


###### Table Setup (DDL)
```sql
CREATE TABLE cd.members (
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
```

#### Modifying Data
###### Question 1: Insert some data into a table
```sql
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES (9, 'Spa', 20, 30, 100000, 800);
```

###### Question 2:  Insert calculated data into a table
```sql
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
SELECT (SELECT max(facid) FROM cd.facilities)+1, 'Spa', 20, 30, 100000, 800;
```

###### Question 3: Update some existing data
```sql
UPDATE cd.facilities
SET initialoutlay = 10000
WHERE facid =1;
,,,

###### Question 4: Update a row based on the contents of another row
```sql
UPDATE cd.facilities facs
SET
membercost = facs2.membercost * 1.1,
guestcost = facs2.guestcost * 1.1
FROM (SELECT * FROM cd.facilities WHERE facid = 0) facs2
WHERE facs.facid = 1;
```

###### Question 5: Delete all bookings
```sql
DELETE FROM cd.bookings;
```

###### Question 6: Delete a member from the cd.members table
```sql
DELETE FROM cd.members
WHERE memid = 37;
```

#### Basics
###### Question 1: Control which rows are retrieved
```sql
SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities
WHERE membercost < (monthlymaintenance/50) AND membercost != 0;
```

###### Question 2: Basic string searches
```sql
SELECT *
FROM cd.facilities
WHERE name LIKE '%Tennis%
```

###### Question 3: Matching against multiple possible values 
```sql
SELECT * FROM cd.facilities
WHERE facid IN (1, 5);
```

###### Question 4: Working with dates
```sql
SELECT memid, surname, firstname, joindate
FROM cd.members
WHERE joindate > TIMESTAMP '2012-09-01 00:00:00';
```

###### Question 5: Combining results from multiple queries
```sql
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;
```

#### Join
###### Question 1: Retrieve the start times of members' bookings
```sql
SELECT bks.starttime
FROM cd.bookings bks
INNER JOIN cd.members mems
ON mems.memid = bks.memid
WHERE mems.firstname='David' AND mems.surname='Farrell';
```

###### Question 2: Work out the start times of bookings for tennis courts
```sql
SELECT bks.starttime, fac.name
FROM cd.bookings AS bks
INNER JOIN cd.facilities AS fac
ON fac.facid = bks.facid
WHERE fac.name LIKE '%Tennis%' AND
bks.starttime >= '2012-09-21' AND bks.starttime < '2012-09-22'
ORDER BY bks.starttime;
```

###### Question 3: Produce a list of all members, along with their recommender
```sql
SELECT mems.firstname AS memfname, mems.surname AS memsname, recs.firstname AS recfname, recs.surname AS recsname
FROM cd.members mems
LEFT OUTER JOIN cd.members recs
ON recs.memid = mems.recommendedby
ORDER BY memsname, memfname;
```

###### Question 4: Produce a list of all members who have recommended another member
```sql
SELECT DISTINCT recs.firstname AS firstname, recs.surname AS surname
FROM cd.members mems
INNER JOIN cd.members recs
ON recs.memid = mems.recommendedby
ORDER BY surname, firstname;
```

###### Question 5: Produce a list of all members, along with their recommender, using no joins
```sql
SELECT DISTINCT mems.firstname || ' ' || mems.surname AS member,
(SELECT recs.firstname || ' ' || recs.surname AS recommender
FROM cd.members recs
WHERE recs.memid = mems.recommendedby    )
FROM cd.members mems
ORDER BY member;
```

#### Aggregation
###### Question 1: Count the number of recommendations each member makes
```sql
SELECT recommendedby, COUNT(*)
FROM cd.members
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;
```

###### Question 2: List the total slots booked per facility
```sql
SELECT facid, SUM(slots) as total_slots
FROM cd.bookings
GROUP BY facid
ORDER BY facid;
```

###### Question 3: List the total slots booked per facility in a given month
```sql
SELECT facid, SUM(slots) AS total_slots
FROM cd.bookings
WHERE starttime >= '2012-09-01' AND starttime < '2012-10-01'
GROUP BY facid
ORDER BY SUM(slots);
```

###### Question 4: List the total slots booked per facility per month
```sql
SELECT facid, EXTRACT(month from starttime) AS month, SUM(slots) AS total_slots
FROM cd.bookings
WHERE EXTRACT(year from starttime) = 2012
GROUP BY facid, month
ORDER BY facid;
```

###### Question 5: Find the count of members who have made at least one booking
```sql
SELECT COUNT(DISTINCT memid)
FROM cd.bookings
```

###### Question 6: List each member's first booking after September 1st 2012
```sql
SELECT mems.surname, mems.firstname, mems.memid, min(bks.starttime) AS starttime
FROM cd.bookings bks
INNER JOIN cd.members mems
ON mems.memid = bks.memid
WHERE starttime >= '2012-09-01'
GROUP BY mems.surname, mems.firstname, mems.memid
ORDER BY mems.memid;
```

###### Question 7: Produce a list of member names, with each row containing the total member count
```sql
SELECT COUNT(*) OVER(PARTITION BY date_trunc('month',joindate)),
firstname, surname
FROM cd.members
ORDER BY joindate;
```

###### Question 8: Produce a numbered list of members
```sql
SELECT row_number() OVER(ORDER BY joindate), firstname, surname
FROM cd.members
ORDER BY joindate
```

###### Question 9: Output the facility id that has the highest number of slots booked
```sql
SELECT facid, total
FROM (SELECT facid, SUM(slots) total, rank() OVER (ORDER BY SUM(slots) DESC) rank
FROM cd.bookings
group by facid) AS ranked
WHERE rank = 1
```

#### String
###### Question 1: Format the names of members
```sql
SELECT surname || ', ' || firstname AS name FROM cd.members;
```

###### Question 2: Find telephone numbers with parentheses
```sql
SELECT memid, telephone
FROM cd.members
WHERE telephone similar to '%[()]%';
```

###### Question 3: Count the number of members whose surname starts with each letter of the alphabet
```sql
SELECT substr (mems.surname,1,1) AS letter, COUNT(*) AS count
FROM cd.members mems
GROUP BY letter
ORDER BY letter;
```



