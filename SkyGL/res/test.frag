#define T_FAR 1000.

#define LIGHT_DIR   0
#define LIGHT_POINT 1
#define LIGHT_AMB   2

const vec3 BACKGROUND_COL = vec3(0.2,0.2,0.5);

uniform float time;
uniform vec2 resolution;

struct HitData {
    float t1;
	float t2;
	vec3 p1;
    vec3 p2;
    vec3 n;
    bool hit;
};

struct Ray {
    vec3 o;
    vec3 d;
};

struct Sphere {
	vec3 c;
    float r;
    vec3 col;
    float specular;
    float reflective;
    float alpha;
};

struct Light {
	vec3 p;
    float intensity;
    int type;
};

mat3 rotateX(float angle) {
    float s = sin(angle);
    float c = cos(angle);
    return mat3(
    	1, 0, 0,
        0, c, -s,
        0, s, c
    );
}

Sphere nullSphere = Sphere(vec3(0),0.,BACKGROUND_COL,0.,0.,0.);

HitData iSphere(Ray r, Sphere sphere) {
	HitData data;
	vec3 co = r.o-sphere.c;
    float a = dot(r.d,r.d);
    float b = 2.*dot(r.d,co);
    float c = dot(co,co) - sphere.r*sphere.r;
    float delta = b*b - 4.*a*c;
    if (delta < 0.) data.hit = false;
    else {
        data.t1 = (-b-sqrt(delta)) / (2.*a);
        data.t2 = (-b+sqrt(delta)) / (2.*a);
        data.p1 = r.o + data.t1*r.d;
        data.n = normalize(data.p1-sphere.c);
		data.hit = true;
    }
    return data;
}

Sphere traceSingleRay(Ray r, out HitData o_hitData) {
	Sphere spheres[3] = Sphere[3](
    	Sphere(vec3(1.5,0,5),1.,vec3(1,0,0),200.,0.,1.),
        Sphere(vec3(-1.5,0,5),1.,vec3(1,1,1),100.,0.,1.),
        Sphere(vec3(0,-5001,1),5000.,vec3(1,0,1),-1,0.6,1.)
    );
    Sphere hitSphere = nullSphere;
    float t = T_FAR;
    o_hitData = HitData(T_FAR,T_FAR,vec3(0),vec3(0),vec3(0),false);
    for (int i = 0; i < spheres.length(); i++) {
    	Sphere sphere = spheres[i];
        HitData hitData = iSphere(r,sphere);
        if (hitData.hit && hitData.t1 < t && hitData.t1 > 0.001) {
        	t = hitData.t1;
            o_hitData = hitData;
            hitSphere = sphere;
        }

        /*if (hitData.hit && hitData.t2 < t && hitData.t2 > 0.001) {
        	t = hitData.t2;
            o_hitData = hitData;
            hitSphere = sphere;
        }*/
    }
    return hitSphere;
}

float computeLighting(vec3 p, vec3 n, vec3 rd, float specular, Sphere sphere) {
	float intensity = 0.;

    Light lights[3] = Light[3](
    	Light(vec3(1,2,5),0.5,LIGHT_POINT),
        Light(vec3(1,0,-1),0.1,LIGHT_DIR),
		Light(vec3(0),0.1,LIGHT_AMB)
    );

    for (int i = 0; i < lights.length(); i++) {
    	Light light = lights[i];
        if (light.type != LIGHT_AMB) {
            vec3 l;
            if (light.type == LIGHT_POINT)
            	l = normalize(light.p-p);
            else if (light.type == LIGHT_DIR)
                l = light.p;

            HitData shadowData;
            Sphere shadowSphere = traceSingleRay(Ray(p+normalize(light.p-p)*0.001,normalize(light.p-p)),shadowData);
            //if (!shadowData.hit) {
                float d = clamp(dot(n,l),0.,1.);
                if (d > 0.)
                    intensity += light.intensity*d/(length(n)*length(l));

                if (specular != -1.) {
                    vec3 r = 2.*n*dot(n,l) - l;
                    d = dot(r,-rd);
                    if (d > 0.)
                        intensity += light.intensity*pow(d/(length(r)*length(-rd)),specular);
                }
            //}
                if (shadowData.hit && light.type == LIGHT_POINT) intensity*=0.5;
        } else {
        	intensity += light.intensity;
        }
    }

    return intensity;
}

vec3 traceReflection(Ray ray, HitData hitData, Sphere hitSphere) {
	vec3 col = vec3(0);
    float r = 0.;
    float rlight = 1.;
    Sphere reflectedSphere;
    for (int i = 0; i < 1; i++) {
    	ray.d = reflect(ray.d,hitData.n);
        Ray ref = Ray(hitData.p1,ray.d);
        HitData refHitData;
        reflectedSphere = traceSingleRay(ref,refHitData);
        if (reflectedSphere.reflective <= 0. && reflectedSphere != nullSphere) {
        	rlight = computeLighting(refHitData.p1, refHitData.n, ref.d, reflectedSphere.specular, reflectedSphere);
            //col += hitSphere.col*(1.-hitSphere.reflective)+(reflectedSphere.col*hitSphere.reflective*rlight);
            r += hitSphere.reflective;
            if (hitSphere.alpha >= 1.)
                col = hitSphere.col*computeLighting(hitData.p1, hitData.n, ref.d, hitSphere.specular, hitSphere)*(1.-r)+(reflectedSphere.col*r*rlight);
            else
                col = BACKGROUND_COL;
            if (reflectedSphere.reflective <= 0.) break;
            hitSphere = reflectedSphere;
            hitData = refHitData;
    	}
    }
    return col;
}

mat3 rotateY(float a) {
	return mat3(
    	cos(a), 0, sin(a),
        0, 1, 0,
        -sin(a), 0, cos(a)
    );
}

void main() {
    vec2 uv = (gl_FragCoord.xy-0.5*resolution.xy)/resolution.y;

    vec3 col = BACKGROUND_COL;
    //vec3 col = texture(iChannel0,uv).xyz;

    vec3 ro = vec3(cos(time)*5.,1,sin(time)*5.+5.);
    //vec3 ro = vec3(0,1,0);
    vec3 rd = normalize(vec3(uv,1))*rotateX(sin(time*0.5)*0.2)*rotateY(-3.14/2.-time);
    Ray r = Ray(ro,rd);
    HitData hitData;
    Sphere hitSphere = traceSingleRay(r,hitData);

    if (hitData.hit) {
        if (hitSphere.reflective > 0.) {
            //col = traceReflection(r,hitData,hitSphere);
            col = vec3(0);
            float r = 0.;
            float rlight = 0.;
            Sphere reflectedSphere;
            for (int i = 0; i < 1; i++) {
                rd = reflect(rd,hitData.n);
                Ray ref = Ray(hitData.p1,rd);
                HitData refHitData;
                reflectedSphere = traceSingleRay(ref,refHitData);
                float rlight = 1.;
                if (reflectedSphere.reflective <= 0. && reflectedSphere != nullSphere)
                    rlight = computeLighting(refHitData.p1, refHitData.n, ref.d, reflectedSphere.specular, hitSphere);
                //col += hitSphere.col*(1.-hitSphere.reflective)+(reflectedSphere.col*hitSphere.reflective*rlight);
                r += hitSphere.reflective;
                if (hitSphere.alpha >= 1.)
                	col = hitSphere.col*computeLighting(hitData.p1, hitData.n, ref.d, hitSphere.specular, hitSphere)*(1.-r)+(reflectedSphere.col*r*rlight);
                else
                	col = BACKGROUND_COL;
                if (reflectedSphere.reflective <= 0.) break;
                hitSphere = reflectedSphere;
                hitData = refHitData;
            }
        } else if (hitSphere.alpha < 1.) {
            Ray ref = Ray(hitData.p2,refract(rd,hitData.n,0.2));
            HitData refHitData;
        	Sphere refractedSphere = traceSingleRay(ref,refHitData);
            if (refHitData.hit)
            	col = refractedSphere.col*computeLighting(hitData.p1, hitData.n, r.d, hitSphere.specular, hitSphere);
        }
        else {
            float light = computeLighting(hitData.p1, hitData.n, r.d, hitSphere.specular, hitSphere);
            col = hitSphere.col*light;
        }
    }

    //if (fragCoord.x > iResolution.x/2.-1. && fragCoord.x < iResolution.x/2.+1.) col = vec3(1,0,0);

    gl_FragColor = vec4(col,1);
}
