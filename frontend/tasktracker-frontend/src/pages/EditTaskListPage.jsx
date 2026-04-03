import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../services/api";

function EditTaskListPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    title: "",
    description: ""
  });

  useEffect(() => {
    const fetch = async () => {
      const res = await API.get(`/task-lists/${id}`);
      setForm({
        title: res.data.title,
        description: res.data.description
      });
    };
    fetch();
  }, [id]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

 const update = async () => {
  try {
    const payload = {
      id: id, // ✅ VERY IMPORTANT
      title: form.title.trim(),
      description: form.description.trim()
    };

    console.log("PAYLOAD:", payload);

    await API.put(`/task-lists/${id}`, payload);

    navigate("/");
  } catch (err) {
    console.error("ERROR:", err.response?.data);
  }
};

  return (
    <div className="container">
      <h1 className="page-title">🌸 Edit Task List</h1>

      <div className="card" style={{ maxWidth: "400px" }}>
        <input
          className="input"
          name="title"
          value={form.title}
          onChange={handleChange}
        />

        <input
          className="input"
          name="description"
          value={form.description}
          onChange={handleChange}
        />

        <button className="button" onClick={update}>
          Update
        </button>
      </div>
    </div>
  );
}

export default EditTaskListPage;