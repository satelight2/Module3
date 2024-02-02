create database if not exists TickelFilm;
use TickelFilm;
drop table Ghe;
create table if not exists Phim (
	PhimID int primary key,
	Ten_Phim nvarchar(255),
	Loai_Phim nvarchar(255),
	Thoi_Gian int);
    
create table if not exists Phong (
PhongID int primary key,
Ten_Phong nvarchar(255),
Trang_Thai bit);


create table if not exists Ghe (
	GheID int primary key,
	PhongID int,
	So_ghe varchar(25));

alter table Ghe 
add foreign key (PhongID) references Phong (PhongID);

create table if not exists Ve (
	PhimID int,
	GheID int,
	Ngay_chieu datetime,
	Trang_thai nvarchar(255));
    
 alter table Ve
 add foreign key (PhimID) references Phim(PhimID);
 
  alter table Ve
 add foreign key (GheID) references Ghe(GheID);
 
 insert into Phim (PhimID, Ten_Phim, Loai_Phim, Thoi_Gian)values(1,'Em Bé Hà Nội', 'Tâm Lý', 90);
 insert into Phim (PhimID, Ten_Phim, Loai_Phim, Thoi_Gian)values(2,'Nhiệm Vụ Bất Khả Thi', 'Hành Động', 100);
 insert into Phim (PhimID, Ten_Phim, Loai_Phim, Thoi_Gian)values(3,'Dị Nhân', 'Viễn Tưởng', 90);
 insert into Phim (PhimID, Ten_Phim, Loai_Phim, Thoi_Gian)values(4,'Cuốn Theo Chiều Gió', 'Tình Cảm', 120);
 
 insert into Phong (PhongID, Ten_Phong, Trang_Thai) values( 1,'Phòng Chiếu 1',1);
 insert into Phong (PhongID, Ten_Phong, Trang_Thai) values( 2,'Phòng Chiếu 2',1);
 insert into Phong (PhongID, Ten_Phong, Trang_Thai) values( 3,'Phòng Chiếu 3',0);
 
 insert into Ghe (GheID, PhongID, So_Ghe) values (1,1,'A3');
 insert into Ghe (GheID, PhongID, So_Ghe) values (2,1,'B5');
 insert into Ghe (GheID, PhongID, So_Ghe) values (3,2,'A7');
 insert into Ghe (GheID, PhongID, So_Ghe) values (4,2,'D1');
 insert into Ghe (GheID, PhongID, So_Ghe) values (5,3,'T2');
 
 insert into Ve (PhimID, GheID, Ngay_Chieu, Trang_Thai) values (1,1,'2008-10-20','Đã Bán');
 insert into Ve (PhimID, GheID, Ngay_Chieu, Trang_Thai) values (1,3,'2008-11-20','Đã Bán');
 insert into Ve (PhimID, GheID, Ngay_Chieu, Trang_Thai) values (1,4,'2008-12-23','Đã Bán');
 insert into Ve (PhimID, GheID, Ngay_Chieu, Trang_Thai) values (2,1,'2009-02-14','Đã Bán');
 insert into Ve (PhimID, GheID, Ngay_Chieu, Trang_Thai) values (3,1,'2009-02-14','Đã Bán');
 insert into Ve (PhimID, GheID, Ngay_Chieu, Trang_Thai) values (2,5,'2009-03-08','Chưa');
 insert into Ve (PhimID, GheID, Ngay_Chieu, Trang_Thai) values (2,3,'2009-03-08','Chưa');
 

-- Hiển thị danh sách các phim (chú ý: danh sách phải được sắp xếp theo trường Thoi_gian)
select * from Phim order by Thoi_gian;

-- Hiển thị Ten_phim có thời gian chiếu dài nhất
select Ten_phim as 'Tên Phim' from Phim order by Thoi_gian desc limit 1;

-- Hiển thị Ten_Phim có thời gian chiếu ngắn nhất
select Ten_phim as 'Tên Phim' from Phim order by Thoi_gian limit 1;

-- Hiển thị danh sách So_Ghe mà bắt đầu bằng chữ ‘A’\
select * from Ghe where so_ghe like 'A%';

-- Sửa cột Trang_thai của bảng tblPhong sang kiểu nvarchar(25)
alter table Phong modify column Trang_Thai nvarchar(25);

-- Cập nhật giá trị cột Trang_thai của bảng tblPhong theo các luật sau:
-- Nếu Trang_thai=0 thì gán Trang_thai=’Đang sửa’
-- Nếu Trang_thai=1 thì gán Trang_thai=’Đang sử dụng’
-- Nếu Trang_thai=null thì gán Trang_thai=’Unknow'
update Phong set Trang_Thai = case 
	when Trang_Thai = 0 then 'Đang Sửa'
    when Trang_Thai = 1 then 'Đang Sử Dụng'
    else 'Unknow'
    end
    where phong.PhongID <6;
    
-- Hiển thị danh sách tên phim mà có độ dài >15 và < 25 ký tự
select *, length(ten_phim) from Phim where length(ten_phim) between 15 and 25;

-- Hiển thị Ten_Phong và Trang_Thai trong bảng tblPhong trong 1 cột với tiêu đề ‘Trạng thái phòng chiếu’
select concat(
P.ten_phong ,' ---> ',
P.Trang_Thai) as 'Trạng Thái Phòng Chiếu'
from phong P;

-- Tạo bảng mới có tên tblRank với các cột sau: STT(thứ hạng sắp xếp theo Ten_Phim), TenPhim, Thoi_gian

CREATE TABLE tblRank (
    STT INT AUTO_INCREMENT PRIMARY KEY,
    TenPhim NVARCHAR(255),
    Thoi_Gian INT
);

INSERT INTO tblRank (TenPhim, Thoi_Gian)
SELECT Ten_Phim, Thoi_Gian
FROM Phim
ORDER BY Ten_Phim;

-- Trong bảng tblPhim : 
-- a. Thêm trường Mo_ta kiểu nvarchar(max) 
alter table Phim add Mo_ta text;

-- b. Cập nhật trường Mo_ta: thêm chuỗi “Đây là bộ phim thể loại ” + nội dung trường LoaiPhim 
Update Phim set Mo_ta = concat('Đây là bộ phim thể loại ',Loai_Phim) where phimID<10;

-- c. Hiển thị bảng tblPhim sau khi cập nhật 
select * from Phim;

-- d. Cập nhật trường Mo_ta: thay chuỗi “bộ phim” thành chuỗi “film” 
update Phim set Mo_Ta = replace(Mo_ta,'bộ phim', 'film') where phimID < 10;

-- e. Hiển thị bảng tblPhim sau khi cập nhật
select * from Phim;

-- Xóa tất cả các khóa ngoại trong các bảng trên.
ALTER TABLE Ve DROP FOREIGN KEY ve_ibfk_1;
ALTER TABLE Ve DROP FOREIGN KEY ve_ibfk_2;

SHOW CREATE TABLE Ghe;

ALTER TABLE Ghe DROP FOREIGN KEY ghe_ibfk_1;

-- Xóa dữ liệu ở bảng tblGhe
truncate table Ghe;

-- Hiển thị ngày giờ hiện tại và ngày giờ hiện tại cộng thêm 5000 phút
select current_time();
select now();
select date_add(now(), interval 5000 minute) AS 'time +5000m';