CREATE DATABASE messengerDB;

GRANT ALTER, SELECT,INSERT,UPDATE,DELETE,CREATE,DROP, INDEX
           ON messengerDB.*
           TO spq@'%'
           IDENTIFIED BY 'spq';

GRANT ALTER, SELECT,INSERT,UPDATE,DELETE,CREATE,DROP, INDEX
           ON messengerDB.*
           TO spq@localhost
           IDENTIFIED BY 'spq';