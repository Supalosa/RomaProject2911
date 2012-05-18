package actiontargets;

import java.util.Map;

public interface IActionTargetHolder {
	
	public Map<Integer, ActionTarget<?>> getParameters();
	
	public ActionTarget<?> getParameter(int index);
	
	/**
	 * Fires after a parameter has been set, gives the opportunity
	 * to add more parameters if necessary.
	 * @param paramIndex
	 * @param param
	 */
	void onParameterSet (int paramIndex, ActionTarget<?> param);
	
}
