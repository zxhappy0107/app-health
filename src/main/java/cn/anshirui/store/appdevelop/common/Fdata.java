package cn.anshirui.store.appdevelop.common;

public class Fdata{
	
	private static Fdata fdata;
	
	public static Fdata getme() {
		if (fdata == null) {
			fdata = new Fdata();
		}
		return fdata;
	}
	
	String[] hrList = {"71","70","72","71","69","72","73","72","71","73","72"};
	String[] btList = {"14","16","17","16","14","15","16","17","16","15","17"};
	String[] bmList = {"4","3","5","3","6","4","3","3","4","5","6"};
	
	public String getFakeData(int timeDiff) {
		String result = null;
		switch (timeDiff) {
		case 0:
			result = hrList[0] + "@@" + btList[0] + "@@" + bmList[0];
			break;
		case 1:
			result = hrList[1] + "@@" + btList[1] + "@@" + bmList[1];
			break;
		case 2:
			result = hrList[2] + "@@" + btList[2] + "@@" + bmList[2];
			break;
		case 3:
			result = hrList[3] + "@@" + btList[3] + "@@" + bmList[3];
			break;
		case 4:
			result = hrList[4] + "@@" + btList[4] + "@@" + bmList[4];
			break;
		case 5:
			result = hrList[5] + "@@" + btList[5] + "@@" + bmList[5];
			break;
		case 6:
			result = hrList[6] + "@@" + btList[6] + "@@" + bmList[6];
			break;
		case 7:
			result = hrList[7] + "@@" + btList[7] + "@@" + bmList[7];
			break;
		case 8:
			result = hrList[8] + "@@" + btList[8] + "@@" + bmList[8];
			break;
		case 9:
			result = hrList[9] + "@@" + btList[9] + "@@" + bmList[9];
			break;
		case 10:
			result = hrList[10] + "@@" + btList[10] + "@@" + bmList[10];
			break;
		default:
			break;
		}
		return result;
	} 
	
}
