
const getUser = async () => {
  const token = sessionStorage.getItem("token");
  if (!token) return null;

  try {
    const response = await fetch("http://localhost:8080/api/v1/auth/users", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    });

    if (!response.ok) return null;

    const data = await response.json();
    return data;
  } catch (err) {
    console.error("Error fetching user:", err);
    return null;
  }
};

export default getUser;
