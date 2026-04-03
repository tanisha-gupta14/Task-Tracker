import { BrowserRouter, Routes, Route } from "react-router-dom";
import TaskListsPage from "./pages/TaskListsPage";
import TasksPage from "./pages/TasksPage";
import CreateTaskPage from "./pages/CreateTaskPage";
import CreateTaskListPage from "./pages/CreateTaskListPage";
import EditTaskListPage from "./pages/EditTaskListPage";
import EditTaskPage from "./pages/EditTaskPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<TaskListsPage />} />
        <Route path="/task-list/:id" element={<TasksPage />} />
        <Route path="/task-list/:id/create" element={<CreateTaskPage />} />
        <Route path="/edit-list/:id" element={<EditTaskListPage />} />
        <Route path="/task-list/:id/edit/:taskId" element={<EditTaskPage />} />
        <Route path="/create-list" element={<CreateTaskListPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;