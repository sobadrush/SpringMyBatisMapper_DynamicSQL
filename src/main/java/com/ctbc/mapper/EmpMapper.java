package com.ctbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ctbc.model.vo.EmpVO;

@Mapper
public interface EmpMapper {
	public List<EmpVO> getEmp();
	public EmpVO getEmp(@Param("empNoGG") Integer empNo);
	public int addEmp(EmpVO empVO);
	public int updateEmp(@Param("empNoGG") EmpVO empVO);
	public int delEmp(int empId);
	public List<EmpVO> getEmpByDateInteval(List<java.sql.Date> dList);
	
	//----﹝進階查詢 - 測試 <choose>...<when>...<otherwise>﹞-----
	public List<EmpVO> getEmpDynamic(
			@Param("empId") Integer empno , 
			@Param("enamePartial") String enamePart , 
			@Param("hiredates") java.sql.Date[] hDates);
}
