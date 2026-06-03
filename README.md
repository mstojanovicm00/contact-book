# Contact Book

A simple application which allows simple
management of a contact book.

## Purpose

The application was created in order to demonstrate
JDBC connection to a local database. The demonstration
was given on exercise sessions of a Database course
(School of Computing, Union University, Belgrade,
Serbia)
in the summer semester of year 2025/2026, week
starting April 27.

Students in a course are expected to use the program
as a skeleton of their own projects for the course. 

Using the program on local machines
for personal purposes could be
considered, but as this is an educational software,
it does not provide all operations usually present
in a contact book, although they could be added in
new versions.

## Setup

In order to run the application,
a few options have to be considered and specified.

Firstly, the application needs to be run on a Java
editing environment (Eclipse, IntelliJ, Netscape
or similar). Java version used for the development
is JDK 11.

Secondly, Maven has to be installed in order to
retrieve dependencies, as can be seen in 
[pom.xml](https://github.com/mstojanovicm00/contact-book/blob/master/pom.xml).
Given pom.xml file also provides the versions
of libraries
which were used for the development.

Additionally, the database has to be configured
prior to the setup. The source file for creating
the database is given in 
[contact_book_db_create.sql](https://github.com/mstojanovicm00/contact-book/blob/master/contact_book_db_create.sql).
The database was written in MySQL dialect. If switching
to another one, using one of the
tools available online to automatically translate
the schema across dialects should be considered, but
not fully relied on, as they can produce errors.

Lastly, the `relational.cfg` and `mongo.cfg` files have to be given on
a source root. They need to be written in
a following format:

```
[dbtype].host = host that hosts the database (ex. localhost)
[dbtype].port = port on which the database is hosted (ex. 3306)
[dbtype].db = contact_book (name of a database)
[dbtype].user = username to access the host (ex. root)
[dbtype].password = password to access the root user
```

Here, `[dbtype]` could be either `relational` or `mongo`.

The rows with starting with `user` and `password` could be
omitted provided they are not needed to connect to
a database.

## Usage

After the steps given
above are taken, any Java IDE should be able to run
the program. The program is intended only for
use in a Java IDE. It does not include a JAR or any
other executable format of an application.