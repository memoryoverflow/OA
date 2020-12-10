
const config = {
    loginPage: '/login',
    res_success_code: 1,
    res_error_code: 0,
    no_login_code: 9,
    no_page: 404,
    active: "dev",
    server: {
        dev: {
            context_path: '/oa',
            address: "http://127.0.0.1:8081",
        },
        prod: {
            context_path: '/oa',
            address: "http://127.0.0.1:8081",
        }
    },
};

exports.config=config
