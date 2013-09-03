package bigface.service.domain;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

	private static final long serialVersionUID = -3068128618727018182L;

	private Long partner_id;
	private String kind;
	private Double lat;
	private Double lon;
	private String object_hash;
	private Double temp;
	private Date created_at;
	private Date updated_at;

	public Event() {
		super();
		object_hash = "";
	}

	public Event(Long partner_id, String kind, Double lat, Double lon,
			String object_hash, Double temp, Date created_at, Date updated_at) {
		super();
		this.partner_id = partner_id;
		this.kind = kind;
		this.lat = lat;
		this.lon = lon;
		this.object_hash = object_hash;
		this.temp = temp;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(Long partner_id) {
		this.partner_id = partner_id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public String getObject_hash() {
		return object_hash;
	}

	public void setObject_hash(String object_hash) {
		this.object_hash = object_hash;
	}

	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((lat == null) ? 0 : lat.hashCode());
		result = prime * result + ((lon == null) ? 0 : lon.hashCode());
		result = prime * result
				+ ((object_hash == null) ? 0 : object_hash.hashCode());
		result = prime * result
				+ ((partner_id == null) ? 0 : partner_id.hashCode());
		result = prime * result + ((temp == null) ? 0 : temp.hashCode());
		result = prime * result
				+ ((updated_at == null) ? 0 : updated_at.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;
		if (lat == null) {
			if (other.lat != null)
				return false;
		} else if (!lat.equals(other.lat))
			return false;
		if (lon == null) {
			if (other.lon != null)
				return false;
		} else if (!lon.equals(other.lon))
			return false;
		if (object_hash == null) {
			if (other.object_hash != null)
				return false;
		} else if (!object_hash.equals(other.object_hash))
			return false;
		if (partner_id == null) {
			if (other.partner_id != null)
				return false;
		} else if (!partner_id.equals(other.partner_id))
			return false;
		if (temp == null) {
			if (other.temp != null)
				return false;
		} else if (!temp.equals(other.temp))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [partner_id=" + partner_id + ", kind=" + kind + ", lat="
				+ lat + ", lon=" + lon + ", object_hash=" + object_hash
				+ ", temp=" + temp + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + "]";
	}

}
