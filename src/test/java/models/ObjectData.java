package models;

import java.util.Map;

public class ObjectData {
    private String name;
    private Map<String, Object> data;
    private boolean is_used;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Map<String, Object> getData() { return data;}
    public void setData(Map<String, Object> data) { this.data = data; }
    
    public boolean isIs_used() { return is_used; }
    public void setIs_used(boolean is_used) { this.is_used = is_used; }
}
