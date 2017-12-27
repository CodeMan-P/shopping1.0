package dllTest;
import com.sun.jna.Library;  
import com.sun.jna.Native; 
public interface DllFun extends Library{
	DllFun dll = Native.loadLibrary("Dll2", DllFun.class);
	public int addNum(int i,int b);
}
