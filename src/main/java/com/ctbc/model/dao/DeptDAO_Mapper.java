package com.ctbc.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import com.ctbc.config.RootConfig;
import com.ctbc.mapper.DeptMapper;
import com.ctbc.model.vo.DeptVO;

@Repository
public class DeptDAO_Mapper {

	@Autowired
	private DeptMapper mapper;

	public List<DeptVO> getAll() {
		return mapper.getDept();
	}

	public List<DeptVO> getAllByMapperAnnotation() {
		return mapper.getAllByMapperAnnotation();
	}

	public DeptVO getOneById(Integer deptNo) {
		return mapper.getDept(deptNo);// <if test="xxx != null">
	}

	public List<DeptVO> getDeptsInMulitId(int[] deptNos) {
		return mapper.getDeptsInMulitId(deptNos);
	}
	
	public int addOneDept(DeptVO deptvo) {
		return mapper.addDept(deptvo);
	}

	public int updateDept(DeptVO deptvo) {
		return mapper.updateDept(deptvo);
	}
	
	public int updateDeptUseSetTag(DeptVO deptvo) {
		return mapper.updateDeptUseSetTag(deptvo);
	}
	
	public int delete(Integer deptNo) {
		return mapper.delDept(deptNo);
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		DeptDAO_Mapper deptDAO2 = context.getBean("deptDAO_Mapper", DeptDAO_Mapper.class);
		// 【新增一筆】
//		int pen = deptDAO2.addOneDept(new DeptVO("應用系統部", "台北南港"));
//		System.out.println("新增成功資料 : " + pen + " 筆");
		// 【更新一筆】
//		int pen = deptDAO2.updateDept(new DeptVO(50,"數位金融部","B棟18樓"));
//		System.out.println("更新成功資料 : " + pen + " 筆");
		// 【更新一筆 2】
//		int pen = deptDAO2.updateDeptUseSetTag(new DeptVO(50,"垃圾信金","B棟10樓"));
//		System.out.println("更新成功資料 : " + pen + " 筆");
		// 【查一筆 by Id】
//		DeptVO vo = deptDAO2.getOneById(10);
//		System.out.println(vo.toString());
		// 【刪除一筆 by Id】
//		int pen = deptDAO2.delete(50);
//		System.out.println("刪除成功資料 : " + pen + " 筆");
		// 【查全部】
		List<DeptVO> alist = deptDAO2.getAll();
		for (DeptVO deptVO : alist) {
			System.err.println(deptVO.toString());
		}
		// 【查多筆By Ids】
//		List<DeptVO> deptVOs = deptDAO2.getDeptsInMulitId(new int[]{10,20});
//		for (DeptVO deptVO : deptVOs) {
//			System.err.println(deptVO.toString());
//		}
		context.close();
	}
}
