import { msgError, user, userResponse } from "../utils/types";
import { User } from "../models/user";
import { NextApiRequest, NextApiResponse } from "next";
import * as bcrypt  from "bcryptjs";
import { sign } from "jsonwebtoken";

export const signup = async (req: NextApiRequest, res: NextApiResponse<userResponse | msgError>)=>{
    try{
        req.body.password = await bcrypt.hash(req.body.password, 12);
        const result = await User.create(req.body);
        console.log(result);
        console.log("user created!");
        res.status(200).json({
            user: result,
            message: "user created",
        });
    }catch(e){
        res.status(500).json({
            message: "something went wrong!",
        })
    }

};

export const editUser = async(req: NextApiRequest, res: NextApiResponse<userResponse | msgError>)=>{
    console.log(req.body);
    const {Fullname, username, password, id } = req.body
    try{    
        const result = await User.findByIdAndUpdate(req.body.id, {Fullname: Fullname, username: username});
        console.log("user.updated!");
        res.status(200).json({
            message: "user updated!",
            user: {Fullname: result.Fullname, username: result.username, password: result.password},
        });
    }catch(e){
        res.status(500).json({
            message: "something went wrong!",
        });
    }
};


export const login = async(req: NextApiRequest, res: NextApiResponse<loginResponse | msgError>)=>{
    try{
        if(req.body.password === '' || !req.body.password)
            res.status(404).json({
                message: "invalide user/password!",
            });
        const { username } = req.body;
        const user = await User.findOne({username,});
        if(!user)
            res.status(404).json({
                message: "invalid user/password"
            });
        const compare = bcrypt.compare(req.body.password, user.password);
        if(!user && !compare)
            res.status(400).json({
                message: "invalid user/password!",
            });
        let token = sign({
            username: user.username,
            password: user.password,
            Fullname: user?.Fullname,
        }, process.env.SECRET_KEY, {expiresIn: "24h"});
        res.status(200).json({
            message: "user logged in!",
            user: {
                username: user.username,
                password: user.password,
                Fullname: user.Fullname
            },
            token,
        });
        
    }catch(e){
        console.log(e);
        res.status(500).json({
            message: "something went wrong!",
        })
    }
};