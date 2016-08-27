package thinkjoy.com.downloadlistdemo.bean;


import java.io.Serializable;

/**
 * 
 * TODO 创建一个下载信息的实体类
 * <p/>
 *
 * @version
 * @since v0.0.1
 */
public class MaterialDownInfoBean implements Serializable {
	private static final long serialVersionUID = 2090092676706030440L;
	private int id; // 主id

	private String docName;

	private String docUrl; //

	private String docSize;

	private int downLoadStatus; // 0表示未下载 ,1表示下载中, 2表示暂停, 3表示已完成

	private int progressSize;// 下载进度大小

	private int startPos;// 开始点

	private int endPos;// 结束点

	private boolean isSelected;// 判断有没有被选中编辑

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getDocSize() {
		return docSize;
	}

	public void setDocSize(String docSize) {
		this.docSize = docSize;
	}

	public int getDownLoadStatus() {
		return downLoadStatus;
	}

	public void setDownLoadStatus(int downLoadStatus) {
		this.downLoadStatus = downLoadStatus;
	}

	public int getProgressSize() {
		return progressSize;
	}

	public void setProgressSize(int progressSize) {
		this.progressSize = progressSize;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
}