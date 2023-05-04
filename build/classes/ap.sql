CREATE DATABASE `ap`;
CREATE TABLE `student` (
  `S_id` varchar(45) NOT NULL,
  `F_Name` varchar(45) NOT NULL,
  `L_Name` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`S_id`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  UNIQUE KEY `S_id_UNIQUE` (`S_id`)
) ;
INSERT INTO ap.student (`S_id`, `F_Name`,`L_Name`, `Email`, `Password`) 
 VALUES ('441015678', 'Reem', 'Alotmi', 'reem@hotmail.com', '123123');

CREATE TABLE `course` (
  `Course_id` varchar(45) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Major` varchar(45) NOT NULL,
  `Level` varchar(45) NOT NULL,
  `Date` date NOT NULL,
  `Verify_Code` varchar(130) NOT NULL,
  `Rating` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Course_id`)
);

INSERT INTO ap.course (`Course_id`, `Name`,`Major`, `Level`, `Date`,`Verify_Code`) 
VALUES ('11345', 'Graphic Design Basics', 'Art', 'Beginner', '2022-11-12', 'Gr11453des'),
('11560', 'Illustrated Environments', 'Art', 'Advanced', '2022-10-29', 'Ill34893zx'),
('11988', 'Learn to Draw', 'Art', 'Beginner', '2022-10-28', 'Jdo1129dj5'),
('11448', 'Introduction to Interior Design', 'Art', 'Beginner', '2022-10-30', 'Fkov387h2h'),
('11777', 'Photoshop with and Adobe illustrator', 'Art', 'Advanced', '2022-05-11', 'Dkl39hg5d9'),
('10349', 'Algorithms and Data Structure', 'Computer Science', 'Advanced', '2022-01-10', 'AaDS1049'),
('10601', 'STEM', 'Computer Science', 'advanced', '2022-11-25', 'S4060147'),
('10691', 'Principal of Computing', 'Computer Science', 'Beginner','2022-12-05', 'Zth11069'),
('10860', 'Programming with Python', 'Computer Science', 'Beginner', '2022-12-15', 'Plc11086'),
('10655', 'Machine Learning', 'Computer Science', 'Advanced','2022-10-05','MlX78900'),
('00004', 'Sales Training', 'Business', 'Advanced', '2022-09-03', 'NLS8729H'),
('00005', 'Essential Public Speaking', 'Business', 'Beginner', '2022-08-10','7927JH23' ),
('00006', 'Brand Identity and Strategy', 'Business', 'Advanced', '2022-12-16', 'GU86FD90'),
('00007', 'Work Smarter, Not Harder', 'Business', 'Beginner', '2022-11-03', '3759G34R'),
('00008', 'Business Management', 'Business', 'Advanced', '2022-12-20', 'OG598F30'),
('12001', 'Basics of Human Genetics', 'Health', 'Beginner', '2022-07-05', 'Ill343zx'),
('12002', 'Cell Physiology', 'Health', 'Advanced', '2022-03-27', 'S40ty78f'),
('12003', 'Biology', 'Health', 'Beginner', '2022-11-11', 'Mth1107l'),
('12004', 'Diabetes – the Essential Facts', 'Health', 'Beginner', '2022-12-09', 'ASc086he'),
('12005', 'Stories of Infection', 'Health', 'Advanced', '2022-10-06', 'Mlc90017'),
('90006', 'Google IT Support', 'Information Technology', 'Advanced', '2022-11-04', 'MMdn1352'),
('19001', 'American Deaf Culture', 'Languages', 'Beginner', '2022-02-12', 'cv7a8890'),
('19011', 'English for Career Development', 'Languages', 'Advanced', '2022-12-12', 'cfkdj890'),
('19071', 'First Step Korean', 'Languages', 'Beginner', '2022-12-28', 'Bdfa6790'),
('17001', 'AI & Law', 'Law', 'Advanced', '2022-03-12', 'cv7a8890'),
('17021', 'Employment Contracts', 'Law', 'beginner', '2022-03-12', 'cv7rw890'),
('14002', 'Successful Negotiation: Essential Skills' , 'Social Science', 'Advanced', '2022-08-14', 'cz445kli'),
('13003', 'Geological Oceanography' , 'Marine Science', 'Beginner', '2022-07-10', 'ky665rdm'),
('13004', 'Marine Engineering', 'Marine Science', 'Advanced', '2022-05-18', 'hf55as27'),
('13005', 'Physical Oceanography', 'Marine Science', 'Advanced', '2022-10-18', 'y145vx89');
  


  
  
  CREATE TABLE `take` (
  `Course_id` varchar(45) NOT NULL,
  `S_id` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`Course_id`,`S_id`),
  KEY `S_id_idx` (`S_id`),
  CONSTRAINT `Course_id` FOREIGN KEY (`Course_id`) REFERENCES `course` (`Course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `S_id` FOREIGN KEY (`S_id`) REFERENCES `student` (`S_id`) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO `ap`.`take` (`Course_id`, `S_id`, `status`)
VALUES('11345','441015678','unconfirmed');





