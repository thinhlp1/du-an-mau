


ALTER PROC sp_ThongKeDoanhThu(@Year INT)
AS BEGIN
  SELECT
	TenCD ChuyenDe,
	COUNT(DISTINCT kh.MaKH) SoKH,
	COUNT(hv.MaHV) SoHV,
	SUM(kh.HocPhi ) DoanhThu ,
	MIN(kh.HocPhi) ThapNhat,
	MAX(kh.HocPhi) CaoNhat,
	AVG(kh.HocPhi) TrungBinh

FROM KhoaHoc kh
	LEFT JOIN HocVien hv ON kh.MaKH=hv.MaKH --join ---> left join
	JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	WHERE YEAR(NgayKG) = 2022
GROUP BY TenCD
END


ALTER PROC [dbo].[sp_ThongKeDiem]
AS BEGIN
	SELECT
		TenCD ChuyenDe,
		COUNT(MaHV) SoHV,
		MIN(Diem) ThapNhat,
		MAX(Diem) CaoNhat,
		AVG(Diem) TrungBinh
	FROM KhoaHoc kh
		LEFT JOIN HocVien hv ON kh.MaKH=hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	GROUP BY TenCD
END

