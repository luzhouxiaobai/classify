package cn.bistu.icdd.gpf.app;

import cn.bistu.icdd.gpf.classifier.RocchioClassifier;
import cn.bistu.icdd.gpf.preprocess.Preprocessor;
import cn.bistu.icdd.gpf.selectFeature.impl.DFCalculater;
import cn.bistu.icdd.gpf.selectFeature.impl.MICalculater;

/**
 * 文本分类程序
 * 
 * @author 关鹏飞
 *
 */
public class TextClassifer {
	
	/**
	 * 文本分类主入口
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 文档根目录
		 String fileRootPath = "D:/NLP/每类个数类似文本分类";
		 //String fileRootPath = "D:/NLP/test";
		 //String fileRootPath = "D:/NLP/tst";
		 //String fileRootPath = "D:/NLP/分类测试";
		// String fileRootPath = "D:/NLP/分类测试2";    // 预处理一个小时   特征提取用时110363（提取时占用1G）
		 //String fileRootPath = "D:/NLP/信息检索与搜索引擎/大作业";
		
		Long startTime = System.currentTimeMillis();
		
		System.out.println("**********************开始预处理**********************");
		
		// 数据预处理
		Preprocessor pre = new Preprocessor("gb2312");
		//Preprocessor pre = new Preprocessor("utf-8");
		int fileNum = pre.start(fileRootPath+"/训练集");
		
		
		System.out.println("***********************开始特征提取*********************");
		/*
		Long startMITime = System.currentTimeMillis();
		MICalculater mi = new MICalculater(fileRootPath+"/训练集预处理");
		mi.calcWeight();
		mi.printOriginFeature();
		mi.extractFeature();
		Long endMITime = System.currentTimeMillis();
		System.out.println("特征提取用时" + (endMITime - startMITime));
		*/
		
		// 特征提取
		DFCalculater df = new DFCalculater(fileRootPath+"/训练集预处理");
		df.calcWeight();
		df.exportCharacterItems();
		df.extractFeature();
		
		
		System.out.println("***********************训练分类器&分类*********************");
		
		// 训练分类器&分类
		RocchioClassifier ro = new RocchioClassifier(fileRootPath+"/训练集预处理", fileRootPath+"/测试集", "gb2312");
		ro.train();
		ro.processClassifier();
		
		
		Long endTime = System.currentTimeMillis();
		System.out.println("共用时：" + (endTime - startTime));
		
	}
}
